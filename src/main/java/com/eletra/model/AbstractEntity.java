package com.eletra.model;

import javax.persistence.*;

public abstract class AbstractEntity {

        private String name;

        public AbstractEntity(String name) {
                this.name = name;
        }
        public AbstractEntity(){}

        public String toString(){
                return name;
        };

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }


}
