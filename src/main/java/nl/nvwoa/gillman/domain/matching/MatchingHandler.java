package nl.nvwoa.gillman.domain.matching;


import nl.nvwoa.gillman.model.CalculationResultCollection;
import nl.nvwoa.gillman.model.FullChart;
import nl.nvwoa.gillman.model.MatchMember;
import nl.nvwoa.gillman.model.MatchSet;
import nl.nvwoa.gillman.util.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchingHandler {
    private final List<MatchSet> allMatchSets = new ArrayList<>();
    @Autowired
    private CalculationResultsReader resultsReader;

    public void handleMatches(final String fileIndicator) {
        final String calculatedFilename = constructCalculatedFilename(fileIndicator);
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
        for (MatchSet matchSet : allMatchSets) {
            totalMatches += matchSet.getNrOfMatches();
        }
        System.out.println("Total sets :" + totalSets);
        System.out.println("Total matches :" + totalMatches);
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

    private String constructCalculatedFilename(final String fileIndicator) {
        return Dictionary.PATH_TO_DATA + fileIndicator + Dictionary.CALCULATED_EXTENSION;
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
            allMatchSets.add(new MatchSet(mother, child, countMatches(father, child)));
        }
		/*
		 * Bepaal vader, bepaald moeder, bepaal lijst met kinderen.
		 * Maak lijsts met sets vader en kind1, vader en kindn, moeder en kind1, moeder en kindn
		 * Vergelijjk sets:zelfde planeet op zelfde index in sequence?
		 * Maak resultaatset en save dit in overall object.
		 */
    }
}