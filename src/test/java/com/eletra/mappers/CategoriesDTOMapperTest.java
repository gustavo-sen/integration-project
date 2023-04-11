package com.eletra.mappers;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
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

public class CategoriesDTOMapperTest {

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

        String jsonOfCategories = "[{\"id\":4,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares TB\"},{\"id\":5,\"lineup\":{\"id\":1,\"name\":\"Ares\"},\"name\":\"Ares THS\"}]";
        LineupDTO aresLineup = new LineupDTO(1,"Ares");
        CategoryDTO[] categoryDTOS = {new CategoryDTO(4,"Ares TB",aresLineup),new CategoryDTO(5,"Ares THS",aresLineup)};

        getRequestMockedStatic.when(() -> GETRequest.getJsonOfEntities(anyString(),any())).thenReturn(jsonOfCategories);

        List<CategoryDTO> expectedCategoryList = Arrays.asList(categoryDTOS);
        List<CategoryDTO> actualCategoryList = CategoriesDTOMapper.getListOfCategoriesFrom(aresLineup);

        Assert.assertEquals(expectedCategoryList,actualCategoryList);

    }
}
