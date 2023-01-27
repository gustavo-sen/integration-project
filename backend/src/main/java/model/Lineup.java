package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lineup_table")
public class Lineup {

    @Id
    @Column(name = "lineup_name")
    private String lineupName;

    public Lineup() {}

    Lineup(String lineupName){
        this.lineupName = lineupName;
    }

    @Override
    public String toString() {
        return lineupName;
    }


}
