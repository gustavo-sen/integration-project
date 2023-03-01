package com.eletra.model;

public class CategoryEntity extends AbstractEntity {

    private LineupEntity lineup;

    CategoryEntity(){}
    CategoryEntity(LineupEntity lineup, String name) {
        super(name);
        this.lineup = lineup;
    }

    public LineupEntity getLineup() {
        return lineup;
    }

    public void setLineup(LineupEntity lineup) {
        this.lineup = lineup;
    }
}
