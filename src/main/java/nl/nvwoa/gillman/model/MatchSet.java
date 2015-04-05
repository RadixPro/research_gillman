package nl.nvwoa.gillman.model;

public class MatchSet {
    private final MatchMember child;
    private final int nrOfMatches;
    private final MatchMember parent;

    public MatchSet(final MatchMember parent, final MatchMember child, final int nrOfMatches) {
        this.parent = parent;
        this.child = child;
        this.nrOfMatches = nrOfMatches;
    }

    public MatchMember getChild() {
        return child;
    }

    public int getNrOfMatches() {
        return nrOfMatches;
    }

    public MatchMember getParent() {
        return parent;
    }
}
