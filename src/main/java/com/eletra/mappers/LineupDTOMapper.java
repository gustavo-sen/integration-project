package com.eletra.mappers;

import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LineupDTOMapper {

    public static List<LineupDTO> getListOfLineups() {
        return new Gson().fromJson(GETRequest.getJsonOfEntities("lineups"),
                new TypeToken<List<LineupDTO>>(){}.getType());
    }

}
