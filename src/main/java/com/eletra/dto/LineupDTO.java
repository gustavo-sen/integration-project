package com.eletra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class LineupDTO extends AbstractDTO {
    public LineupDTO(long id, String name) {
        super(id, name);
    }
}
