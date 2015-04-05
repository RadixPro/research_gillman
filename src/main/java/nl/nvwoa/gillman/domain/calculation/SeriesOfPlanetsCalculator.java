package nl.nvwoa.gillman.domain.calculation;

import nl.nvwoa.gillman.model.*;
import nl.nvwoa.gillman.util.AstroMath;
import nl.nvwoa.gillman.util.Conversions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swisseph.SweDate;

import java.util.ArrayList;
import java.util.List;

// Calculates planets and their sequence
@Component
public class SeriesOfPlanetsCalculator {
    @Autowired
    private RisingSequenceSorter risingSequenceSorter;
    @Autowired
    private SECalculator seCalculator;

    public List<FullChart> calculateSet(final InputDataCollection inputDataCollection, CalculationTypes calculationType) {
        List<FullChart> allCharts = new ArrayList<>();
        List<InputData> allInputData = inputDataCollection.getAllInputData();
        for (InputData inputData : allInputData) {
            allCharts.add(calcFullChart(inputData, calculationType));
        }
        return allCharts;
    }

    private FullChart calcFullChart(final InputData inputData, CalculationTypes calculationType) {
        double geoLat = inputData.getGeographicLatitude();
        double geoLon = inputData.getGeographicLongitude();
        SweDate sweDate = Conversions.simpleDateTime2SweDate(inputData.getSimpleDateTime());
        double[] ascMc = seCalculator.calcAscMc(sweDate, geoLon, geoLat);
        List<CalculatedPosition> calculatedPositions = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            calculatedPositions.add(seCalculator.calculatePlanet(i, sweDate, geoLon, geoLat, calculationType));
        }
        FullChart fullChart = new FullChart();
        fullChart.setFamilyId(inputData.getGroupId());
        fullChart.setFamilyRole(inputData.getGroupMemberType());
        fullChart.setId(inputData.getId());
        fullChart.setEpsilon(seCalculator.calculateObliquity(sweDate));
        fullChart.setLongitudeAsc(ascMc[0]);
        fullChart.setLongitudeMC(ascMc[1]);
        fullChart.setRaMc(ascMc[2]);
        fullChart.setRaAsc(AstroMath.rightAscensionForEclipticalPoint(fullChart.getLongitudeAsc(), fullChart.getEpsilon()));
        fullChart.setCalculatedPositions(calculatedPositions);
        fullChart.setPlanetarySequence(calcPlanetarySequence(fullChart));
        fullChart.setFamilyId(inputData.getGroupId());
        // fullChart.setPlanetarySequence(calcPlanetarySequence(fullChart, geoLat));
        return fullChart;
    }

    private PlanetarySequence calcPlanetarySequence(final FullChart fullChart) {
        List<CalculatedPosition> positions = fullChart.getCalculatedPositions();
        List<SortablePosition> sortablePositions = defineSortablePositions(positions);
        List<SortablePosition> sortedPositions = risingSequenceSorter.sortRisingSequence(sortablePositions, fullChart.getRaAsc());
        Bodies[] bodiesInSequence = new Bodies[sortedPositions.size()];
        int counter = 0;
        for (SortablePosition position : sortedPositions) {
            bodiesInSequence[counter++] = position.getBody();
        }
        PlanetarySequence planSequence = new PlanetarySequence();
        planSequence.setFamilyGroupId(fullChart.getFamilyId());
        planSequence.setId(fullChart.getId());
        planSequence.setBodiesInSequence(bodiesInSequence);
        return planSequence;
    }

    private List<SortablePosition> defineSortablePositions(final List<CalculatedPosition> calculatedPositions) {
        List<SortablePosition> sortablePositions = new ArrayList<>();
        for (CalculatedPosition calculatedPosition : calculatedPositions) {
            Bodies body = calculatedPosition.getBody();
            double position4Sorting = calculatedPosition.getRightAscension();
            sortablePositions.add(new SortablePosition(body, position4Sorting, calculatedPosition.getRisingTime()));
        }
        return sortablePositions;
    }
}
