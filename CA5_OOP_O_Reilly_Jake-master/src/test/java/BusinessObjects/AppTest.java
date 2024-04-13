package BusinessObjects;

import Server.DAOs.InstrumentDaoInterface;
import Server.DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Server.Exceptions.DaoException;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    //Reset database with sql before test

    App app = new App();

    // Ciara
    @Test
    void findPlayersUsingFilter() throws DaoException {
        ComparePrice cp = new ComparePrice();
        List<Instrument> filteredInstruments = app.findPlayersUsingFilter(cp);

        //instrument with smallest price

        assertEquals(9, filteredInstruments.get(0).getId());
    }

    // Felix
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

    // Jake
    @Test
    void testInstrument1ToJSON() throws DaoException {
        JsonConverter jc = new JsonConverter();
        Instrument instrument = app.InstrumentDao.getInstrumentById(1);

        String expected = "{\"id\":1,\"name\":\"Fender Vintera 50s Telecaster MN, Sonic Blue\",\"price\":894.0,\"type\":\"GUITAR\"}";
        String actual = jc.instrumentToJSON(instrument);

        assertEquals(expected, actual);
    }

    // Jake
    // Instrument 9 outputs the float value to an inaccurate float, so I wanted to see if the JUnit lets it pass
    // After running: the output of the float value is 13.949999809265137 instead of 13.95 like the SQL says, maybe we have to reformat the SQL building code?
    @Test
    void testInstrument9ToJSON() throws DaoException {
        JsonConverter jc = new JsonConverter();
        Instrument instrument = app.InstrumentDao.getInstrumentById(9);

        String expected = "{\"id\":9,\"name\":\"Fender Blues Deluxe Harmonica, A\",\"price\":13.95,\"type\":\"WIND\"}";
        String actual = jc.instrumentToJSON(instrument);

        assertEquals(expected, actual);
    }
}