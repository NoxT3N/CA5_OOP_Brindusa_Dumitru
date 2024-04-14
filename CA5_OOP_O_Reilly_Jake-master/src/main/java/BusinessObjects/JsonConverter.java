package BusinessObjects;
//File started by Brindusa Dumitru
import DTOs.Instrument;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class JsonConverter {
    private static JsonConverter instance;
    Gson gson;
    private JsonConverter() {
        this.gson = new Gson();
    }

    public static synchronized JsonConverter getInstance(){
        if(instance == null){
            instance = new JsonConverter();
        }
        return instance;
    }

    // Feature 7 - Convert List of Entities to a JSON String
    // e.g. String playersListToJson( List<Player> list )
    // Brindusa Dumitru
    public String instrumentListToJSON(List<Instrument> instruments){
        return gson.toJson(instruments);
    }

    // Feature 8 – Convert a single Entity by Key as a JSON String
    // e.g. String playerToJson( Player p )
    // Jake O'Reilly
    public String instrumentToJSON(Instrument i){
        return gson.toJson(i);
    }

    //Brindusa Dumitru
    public Instrument JSONtoInstrument(String j){
        return gson.fromJson(j,Instrument.class);
    }

    public List<Instrument> JSONtoInstrumentList(String j){
        Type listType = new TypeToken<LinkedList<Instrument>>(){}.getType();
        return gson.fromJson(j,listType);
    }

}
