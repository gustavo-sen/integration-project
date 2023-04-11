package com.eletra.mappers;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModelDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class ModelDTOMapperTest {

    MockedStatic<GETRequest> getRequestMockedStatic;

    @Before
    public void setUp(){
        getRequestMockedStatic = Mockito.mockStatic(GETRequest.class);
    }

    @After
    public void finish(){
        getRequestMockedStatic.close();
    }

    @Test
    public void getListOfCategoriesFromTest(){

        String jsonOfCategories =
                "[" +
                "{\"id\":12,\"category\":{\"id\":5,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares THS\"},\"name\":\"Ares 7021\"}" +
                ",{\"id\":13,\"category\":{\"id\":5,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares THS\"},\"name\":\"Ares 7031\"}" +
                "]";
        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};
        ModelDTO[] modelDTOS = {new ModelDTO(12, "Ares 7021", categoryDTOS[0]),new ModelDTO(13,"Ares 7031",categoryDTOS[1])};

        getRequestMockedStatic.when(() -> GETRequest.getJsonOfEntities(anyString(),any())).thenReturn(jsonOfCategories);

        List<ModelDTO> expectedModelList = Arrays.asList(modelDTOS);
        List<ModelDTO> actualModelList = ModelDTOMapper.getListOfModelsFrom(categoryDTOS[0]);

        Assert.assertEquals(expectedModelList,actualModelList);

    }

}
