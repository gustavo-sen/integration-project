package com.eletra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractDTO {
        private long id;
        private String name;
        public String toString(){
                return name;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj == this) {
                        return true;
                }

                if (!(obj instanceof AbstractDTO)) {
                        return false;
                }

                AbstractDTO otherObject = (AbstractDTO) obj;

                return Objects.equals(name, otherObject.name)
                        && Objects.equals(id, otherObject.id);
        }
}
