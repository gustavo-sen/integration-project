package com.eletra.model;

public class modelDTO extends AbstractDTO {

    private CategoryDTO category;

    public modelDTO() {}

    modelDTO(CategoryDTO category, String name) {
        super(name);
        this.category = category;
    }

    public CategoryDTO getCategories() {
        return category;
    }

    public void setCategories(CategoryDTO category) {
        this.category = category;
    }
}
