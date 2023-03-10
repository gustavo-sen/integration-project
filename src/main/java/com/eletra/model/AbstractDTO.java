package com.eletra.model;

public abstract class AbstractDTO {

        private String name;

        public AbstractDTO(String name) {
                this.name = name;
        }

        public AbstractDTO(){}

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
