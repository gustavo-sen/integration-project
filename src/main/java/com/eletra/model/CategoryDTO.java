package com.eletra.model;



public class CategoryDTO extends AbstractDTO {

    private LineupDTO lineup;

    CategoryDTO(){}
    CategoryDTO(LineupDTO lineup, String name) {
        super(name);
        this.lineup = lineup;
    }

    public LineupDTO getLineup() {
        return lineup;
    }

    public void setLineup(LineupDTO lineup) {
        this.lineup = lineup;
    }
}
