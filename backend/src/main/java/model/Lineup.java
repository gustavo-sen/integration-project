package model;

public enum Lineup {
    CRONOS("CRONOS"),
    ARES(  "ARES");
    private final String lineupName;

    Lineup(String lineupName){
        this.lineupName = lineupName;
    }

    @Override
    public String toString() {
        return lineupName;
    }


}
