package com.eletra.mapper;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ModelMapperDTO {
    public static List<ModelDTO> getListOfModelsFrom(CategoryDTO fromEntity){
        return new Gson().fromJson(GETRequest.getJsonOfEntities("models",fromEntity.getName()), new TypeToken<List<ModelDTO>>(){}.getType());
    }
}
