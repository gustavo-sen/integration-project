package com.eletra.model;

import javax.persistence.*;

public class ModelEntity extends AbstractEntity{

    private CategoryEntity category;

    public ModelEntity() {}

    ModelEntity(CategoryEntity category, String name) {
        super(name);
        this.category = category;
    }

    public CategoryEntity getCategories() {
        return category;
    }

    public void setCategories(CategoryEntity category) {
        this.category = category;
    }
}
