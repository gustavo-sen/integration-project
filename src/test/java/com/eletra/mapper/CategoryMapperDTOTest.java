package com.eletra.mapper;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryMapperDTOTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    public void getJsonOfEntitiesTest01(){

        String json = "[{\"id\":4,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares TB\"},{\"id\":5,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares THS\"}]";

        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {
                new CategoryDTO(4,"Ares TB",aresLineup)
                ,new CategoryDTO(5,"Ares THS",aresLineup)
        };

        List<CategoryDTO> categoryDTOListExpected = new ArrayList<>(Arrays.asList(categoryDTOS));



      /*  try(MockedStatic<GETRequest> getRequestMockedStatic = Mockito.mockStatic(GETRequest.class)){
            getRequestMockedStatic.when(() -> GETRequest.getJsonOfEntities(null,null)).thenReturn(GETRequest.getJsonFormatted(json));

            List<CategoryDTO> categoryDTOList = CategoryMapperDTO.getListOfCategoriesFrom(aresLineup);

            Assert.assertEquals("Check if List is generated",categoryDTOListExpected,categoryDTOList);
        }*/

    }

}
