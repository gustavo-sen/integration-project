package model;

public enum Categories {

    ARES_TB(   Lineup.ARES,   "ARES TB"),
    ARES_THS(  Lineup.ARES,   "ARES THS"),
    CRONOS_OLD(Lineup.CRONOS, "CRONOS OLD"),
    CRONOS_L(  Lineup.CRONOS, "CRONOS L"),
    CRONOS_NG( Lineup.CRONOS, "CRONOS NG");


    private final String name;
    private final Lineup lineup;

    Categories(Lineup lineup, String name) {
        this.lineup = lineup;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Lineup getLineup() {
        return lineup;
    }
}
