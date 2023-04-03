package com.eletra.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractDTO {
    private LineupDTO lineup;

    public CategoryDTO(long id, String name, LineupDTO lineup) {
        super(id,name);
        this.lineup = lineup;
    }
}
