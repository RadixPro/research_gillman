package nl.nvwoa.gillman.domain.calculation;


import nl.nvwoa.gillman.model.CalculationResultCollection;
import nl.nvwoa.gillman.model.CalculationTypes;
import nl.nvwoa.gillman.model.FullChart;
import nl.nvwoa.gillman.model.InputDataCollection;
import nl.nvwoa.gillman.util.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculationHandler {
    @Autowired
    private SeriesOfPlanetsCalculator calculator;
    @Autowired
    private CalculationResults2JsonConverter converter;
    @Autowired
    private InputDataCollectionReader inputReader;

    public void performCalculation(final String fileIndicator, final CalculationTypes calculationType) {
        String selectedFilename = constructSelectedFilename(fileIndicator);
        String calculatedFilename = constructCalculatedFilename(fileIndicator);
        calculateIt(selectedFilename, calculatedFilename, calculationType, fileIndicator);
        selectedFilename = constructSelectedFilenameForControlGroup(fileIndicator);
        calculatedFilename = constructCalculatedFilenameForControlGroup(fileIndicator);
        calculateIt(selectedFilename, calculatedFilename, calculationType, fileIndicator + "-control");
    }



    private void calculateIt(final String selectedFilename, final String calculatedFilename, final CalculationTypes calculationType, final String fileIndicator) {

        final InputDataCollection inputDataCollection = inputReader.readInputData(selectedFilename);
        final List<FullChart> calculatedCharts = calculator.calculateSet(inputDataCollection, calculationType);
        final CalculationResultCollection calculationResultCollection = new CalculationResultCollection(); // TODO autowire, no singleton
        calculationResultCollection.setDescription("Calculated results for : " + fileIndicator + ". CalculationType : " + calculationType.getAbbreviation());
        calculationResultCollection.setAllFullCharts(calculatedCharts);
        converter.convert(calculatedFilename, calculationResultCollection); // TODO use separate class to write to file
    }

    private String constructCalculatedFilenameForControlGroup(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CONTROL_GROUP_CALCULATED_EXTENSION;
    }

    private String constructCalculatedFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CALCULATED_EXTENSION;
    }

    private String constructSelectedFilenameForControlGroup(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CONTROL_GROUP_EXTENSION;
    }

    private String constructSelectedFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.SELECTED_EXTENSION;
    }
}
