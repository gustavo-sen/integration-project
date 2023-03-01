package com.eletra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class LineupEntity extends AbstractEntity {
    
    public LineupEntity() {}

    LineupEntity(String name){
       super(name);
    }

}
