package com.eletra.mappers;

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

import static org.mockito.ArgumentMatchers.anyString;

public class LineupDTOMapperTest {

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
    public void getListOfLineupsTest(){

        String jsonOfLineups = "[{\"id\":1,\"name\":\"Ares\"},{\"id\":2,\"name\":\"Cronos\"}]";
        LineupDTO[] lineups = {new LineupDTO(1,"Ares"),new LineupDTO(2,"Cronos")};

        getRequestMockedStatic.when(() -> GETRequest.getJsonOfEntities(anyString())).thenReturn(jsonOfLineups);

        List<LineupDTO> expectedCategoryList = Arrays.asList(lineups);
        List<LineupDTO> actualLineupList = LineupDTOMapper.getListOfLineups();

        Assert.assertEquals(expectedCategoryList,actualLineupList);

    }

}
