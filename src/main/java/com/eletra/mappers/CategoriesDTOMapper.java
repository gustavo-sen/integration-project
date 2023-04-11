package com.eletra.mappers;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CategoriesDTOMapper {

    public static List<CategoryDTO> getListOfCategoriesFrom(LineupDTO fromEntity){
        return new Gson().fromJson(GETRequest.getJsonOfEntities("categories"
                ,fromEntity.getName()), new TypeToken<List<CategoryDTO>>(){}.getType());
    }

}
