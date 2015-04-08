package nl.nvwoa.gillman.domain.matching;


import nl.nvwoa.gillman.model.*;
import nl.nvwoa.gillman.util.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchingHandler {
    private List<MatchSet> allMatchSets;

    @Autowired
    private CalculationResultsReader resultsReader;
    @Autowired
    private MatchData2JsonConverter converter;

    public void handleMatches(final String fileIndicator, CalculationTypes calculationType) {
        allMatchSets = new ArrayList<>();
        String calculatedFilename = constructCalculatedFilename(fileIndicator);
        String outputFilename = constructOutputFilename(fileIndicator, calculationType);
        performMatching(fileIndicator, calculationType, calculatedFilename, outputFilename);


        allMatchSets = new ArrayList<>();
        calculatedFilename = constructCalculatedFilenameForControlGroup(fileIndicator);
        outputFilename = constructOutputFilenameForControlGroup(fileIndicator, calculationType);
        performMatching(fileIndicator, calculationType, calculatedFilename, outputFilename);
    }





    private void performMatching(final String fileIndicator, final CalculationTypes calculationType, final String calculatedFilename, final String outputFilename) {
        List<MatchSet> currentMatchSets = new ArrayList<>();
        final CalculationResultCollection resultsCollection = resultsReader.readCalculationResultsData(calculatedFilename);
        // TODO resultsCollection analyseren op matches en totalen en specs berekenen en opslaan.
        List<FullChart> charts = resultsCollection.getAllFullCharts();
        String currentFamily = "";
        List<FullChart> familySet = null;
        for (FullChart chart : charts) {
            if ((familySet != null) && currentFamily.equalsIgnoreCase(chart.getFamilyId())) { // uitbreiden bestaande family
                familySet.add(chart);
            } else {
                if (familySet != null) { // geen match, dus nieuwe family, onder handen family afronden
                    handleExistingSet(familySet);
                    familySet = new ArrayList<>(); // volgende family maken

                }
                familySet = new ArrayList<>(); // eerste family maken
                familySet.add(chart);
                currentFamily = chart.getFamilyId();
            }
        }
        int totalSets = allMatchSets.size();
        int totalMatches = 0;
        int totalWithAtLeastOneMatch = 0;
        for (MatchSet matchSet : allMatchSets) {
            totalMatches += matchSet.getNrOfMatches();
            if  (matchSet.getNrOfMatches() > 0) {
                totalWithAtLeastOneMatch++;
            }
        }
        System.out.println("Total sets :" + totalSets);
        System.out.println("Total matches :" + totalMatches);

        MatchData matchData = new MatchData();
        matchData.setMatchSets(allMatchSets);
        matchData.setDescription(fileIndicator);  // TODO add calculationType
        matchData.setNumberOfMatches(totalMatches);
        matchData.setNumberOfMatchPairs(totalSets);
        matchData.setTotalWithAtLeastOneMatch(totalWithAtLeastOneMatch);
        converter.convert(outputFilename, matchData);
        /*
		 * Lees horoscopen en combineer mensen uit dezelfde familie
		 * maak voor elke familie te vergelijken setjes (F en M elk met alle kinderen) ==> vergelijkingsobject
		 * bereken voor elke set het aantal matches
		 * ]
		 * ]
		 * bereken aantal sets en aantal matches ==> oplsaan in overall object AllMatches
		 * wegschrijven naar bestand
		 */
    }

    private String constructOutputFilename(String fileIndicator, CalculationTypes calculationType) {
            return Dictionary.PATH_TO_DATA + fileIndicator + "-" + calculationType.getAbbreviation() +  ".match.json";
        }

    private String constructOutputFilenameForControlGroup(String fileIndicator, CalculationTypes calculationType) {
        return Dictionary.PATH_TO_DATA + fileIndicator + "-" + calculationType.getAbbreviation() +  ".control.match.json";
    }

    private String constructCalculatedFilename(String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CALCULATED_EXTENSION;
    }

    private String constructCalculatedFilenameForControlGroup(String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CONTROL_GROUP_CALCULATED_EXTENSION;
    }

    private int countMatches(final MatchMember parent, final MatchMember child) {

        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (parent.getSequence().getBodiesInSequence()[i] == child.getSequence().getBodiesInSequence()[i]) {
                counter++;
            }
        }
        return counter;
    }

    private void handleExistingSet(final List<FullChart> familySet) {
        MatchMember father = null;
        MatchMember mother = null;
        List<MatchMember> children = new ArrayList<>();
        for (FullChart chart : familySet) {
            if (chart.getFamilyRole().equalsIgnoreCase("F")) {
                father = new MatchMember(chart.getId(), chart.getFamilyId(), chart.getFamilyRole(), chart.getPlanetarySequence());
            }
            if (chart.getFamilyRole().equalsIgnoreCase("M")) {
                mother = new MatchMember(chart.getId(), chart.getFamilyId(), chart.getFamilyRole(), chart.getPlanetarySequence());
            }
            if (chart.getFamilyRole().equalsIgnoreCase("D") || chart.getFamilyRole().equalsIgnoreCase("S")) {
                children.add(new MatchMember(chart.getId(), chart.getFamilyId(), chart.getFamilyRole(), chart.getPlanetarySequence()));
            }
        }
        for (MatchMember child : children) {
            allMatchSets.add(new MatchSet(father, child, countMatches(father, child)));
            allMatchSets.add(new MatchSet(mother, child, countMatches(mother, child)));
        }

    }
}