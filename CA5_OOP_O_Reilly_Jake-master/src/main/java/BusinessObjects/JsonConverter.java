package BusinessObjects;

import DTOs.Instrument;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {
    public JsonConverter() {
    }

    //    Feature 7 - Convert List of Entities to a JSON String
//    e.g. String playersListToJson( List<Player> list )
//    Brindusa Dumitru
    public String toJSON(List<Instrument> instruments){
        Gson gson = new Gson();
        return gson.toJson(instruments);
    }

    //    Feature 8 – Convert a single Entity by Key as a JSON String
//    e.g. String playerToJson( Player p )
//    Jake Jake O'Reilly
    public String toJSON(Instrument i){
        return "";
    }
}
