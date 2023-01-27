package model;

import javax.persistence.*;

@Entity
@Table(name = "models_table")
public class Models {

    @Id
    @Column(name = "model_name")
    private String modelName;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Categories categories;

    public Models() {}

    Models(Categories categories, String modelName) {
        this.categories = categories;
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return modelName;
    }

    public Categories getCategories() {
        return categories;
    }
}
