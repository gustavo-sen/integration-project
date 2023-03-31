package java.com.eletra.helper.db;

import com.eletra.dto.LineupDTO;
import com.eletra.helper.db.GETRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.List;

public class GETResquestTest {

    @Spy
    List<LineupDTO> lineupDTOList = GETRequest.getListOfLineups();

    @Test
    public void deveraPegarUmJson(){

        lineupDTOList.size();

        //Mockito.when(this.).thenReturn()

    }

}
