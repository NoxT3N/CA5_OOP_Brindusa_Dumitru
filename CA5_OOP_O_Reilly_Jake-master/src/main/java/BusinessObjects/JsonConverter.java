package BusinessObjects;

import DTOs.Instrument;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {
    Gson gson;
    public JsonConverter() {
        this.gson = new Gson();
    }

    //    Feature 7 - Convert List of Entities to a JSON String
//    e.g. String playersListToJson( List<Player> list )
//    Brindusa Dumitru
    public String instrumentListToJSON(List<Instrument> instruments){
        return gson.toJson(instruments);
    }

    //    Feature 8 – Convert a single Entity by Key as a JSON String
//    e.g. String playerToJson( Player p )
//    Jake O'Reilly
    public String instrumentToJSON(Instrument i){
        return "";
    }
}
