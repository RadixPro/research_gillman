package nl.nvwoa.gillman.model;

import java.util.List;


public class MatchData {

    private List<MatchSet> matchSets;
    private int numberOfMatchPairs;
    private int numberOfMatches;
    private int totalWithAtLeastOneMatch;
    private int totalWithAtLEastOneMatchFor2Parents;
    private String description;

    public int getTotalWithAtLEastOneMatchFor2Parents() {
        return totalWithAtLEastOneMatchFor2Parents;
    }

    public void setTotalWithAtLEastOneMatchFor2Parents(int totalWithAtLEastOneMatchFor2Parents) {
        this.totalWithAtLEastOneMatchFor2Parents = totalWithAtLEastOneMatchFor2Parents;
    }

    public int getTotalWithAtLeastOneMatch() {
        return totalWithAtLeastOneMatch;
    }

    public void setTotalWithAtLeastOneMatch(int totalWithAtLeastOneMatch) {
        this.totalWithAtLeastOneMatch = totalWithAtLeastOneMatch;
    }

    public List<MatchSet> getMatchSets() {
        return matchSets;
    }

    public void setMatchSets(List<MatchSet> matchSets) {
        this.matchSets = matchSets;
    }

    public int getNumberOfMatchPairs() {
        return numberOfMatchPairs;
    }

    public void setNumberOfMatchPairs(int numberOfMatchPairs) {
        this.numberOfMatchPairs = numberOfMatchPairs;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
