package BusinessObjects;

import DAOs.InstrumentDaoInterface;
import DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Exceptions.DaoException;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    //Reset database with sql before test

    App app = new App();
    InstrumentDaoInterface InstrumentDao = new MySqlInstrumentDao();

    @Test
    void findPlayersUsingFilter() throws DaoException {
        ComparePrice cp = new ComparePrice();
        List<Instrument> filteredInstruments = app.findPlayersUsingFilter(cp);

        //instrument with smallest price

        assertEquals(9, filteredInstruments.get(0).getId());
    }

    @Test
    void testListToJSON() throws DaoException {
        JsonConverter jc = new JsonConverter();
        List<Instrument> instruments = new LinkedList<>();
        instruments.add(app.InstrumentDao.getInstrumentById(1));
        instruments.add(app.InstrumentDao.getInstrumentById(2));
        instruments.add(app.InstrumentDao.getInstrumentById(3));

        String expected = "[{\"id\":1,\"name\":\"Fender Vintera 50s Telecaster MN, Sonic Blue\",\"price\":894.0,\"type\":\"GUITAR\"},{\"id\":2,\"name\":\"Epiphone Les Paul Muse, Purple Passion Metallic\",\"price\":468.0,\"type\":\"GUITAR\"},{\"id\":3,\"name\":\"Ibanez FRH10N-RGF, Rose Gold Metallic Flat\",\"price\":563.0,\"type\":\"GUITAR\"}]";
        String actual = jc.instrumentListToJSON(instruments);

        assertEquals(expected,actual);

    }
}