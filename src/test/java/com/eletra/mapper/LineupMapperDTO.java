package com.eletra.mapper;

import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LineupMapperDTO {

    public static List<LineupDTO> getListOfLineups() {
        return new Gson().fromJson(GETRequest.getJsonOfEntities("lineups"), new TypeToken<List<LineupDTO>>(){}.getType());
    }

    public static List<LineupDTO> getListOfAllLineups(){
        return new Gson().fromJson(GETRequest.getJsonOfEntities("lineups"), new TypeToken<List<LineupDTO>>(){}.getType());
    }

}


