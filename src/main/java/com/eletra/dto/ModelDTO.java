package com.eletra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO extends AbstractDTO {
    private CategoryDTO categoryDTO;

    public ModelDTO(long id, String name, CategoryDTO categoryDTO) {
        super(id, name);
        this.categoryDTO = categoryDTO;
    }
}
