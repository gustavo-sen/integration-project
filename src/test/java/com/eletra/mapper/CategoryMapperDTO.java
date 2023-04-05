package com.eletra.mapper;

import com.eletra.controller.MainController;
import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.*;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.spy;

public class CategoryMapperDTO {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    public void getJsonOfEntitiesTest01(){
        String json = "[" +
                "{\"id\":4,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares TB\"},{\"id\":5,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares THS\"}]";

        LineupDTO aresLineup = new LineupDTO(1,"Ares");

        CategoryDTO[] categoryDTOS = {
                new CategoryDTO(4,"Ares TB",aresLineup)
                ,new CategoryDTO(5,"Ares THS",aresLineup)
        };

        List<CategoryDTO> categoryDTOList = new ArrayList<>(Arrays.asList(categoryDTOS));

        try(MockedStatic<GETRequest> getRequestMockedStatic = Mockito.mockStatic(GETRequest.class)){
            getRequestMockedStatic.when(() -> GETRequest.).thenReturn();

            List<CategoryDTO> categoryDTOListReal = CategoryMapperDTO.getListOfCategoriesFrom(aresLineup);
            return new Gson().fromJson(GETRequest.getJsonOfEntities("categories",fromEntity.getName()), new TypeToken<List<CategoryDTO>>(){}.getType());

            Assert.assertEquals("Check if List is generated",categoryDTOList,categoryDTOListReal);
        }
    }

}
