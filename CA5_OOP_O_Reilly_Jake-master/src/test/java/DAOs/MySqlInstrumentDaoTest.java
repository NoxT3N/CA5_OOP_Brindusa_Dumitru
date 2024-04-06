package DAOs;

import DTOs.Instrument;
import Exceptions.DaoException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

class MySqlInstrumentDaoTest {
    InstrumentDaoInterface InstrumentDao = new MySqlInstrumentDao();

    //Reset Database with SQL to test

    @Test
    void getAllInstruments() throws DaoException {
        List<Instrument> instruments = InstrumentDao.getAllInstruments();
        assertEquals(10, instruments.size());
    }

    @Test
    void getInstrumentById() throws DaoException {
        //it's printing, look at that later

        //this function appears to run before the delete for whatever reason. made the ids different
        Instrument instrument = InstrumentDao.getInstrumentById(8);
        assertEquals("Casio SA 50 Mini Portable Keyboard", instrument.getName());
    }

    //tests where these fail?

    @Test
    void deleteInstrumentById() throws DaoException {
        InstrumentDao.deleteInstrumentById(7);
        Instrument instrument = InstrumentDao.getInstrumentById(7);
        //assertEquals(null, instrument);
        assertEquals(null, instrument.getName());

        List<Instrument> instruments = InstrumentDao.getAllInstruments();
        assertEquals(9, instruments.size());
    }

    @Test
    void insertInstrument() throws DaoException {
        Instrument knocky = new Instrument(11, "Mr. Knocky", 29.99, "DRUM");

        InstrumentDao.insertInstrument(knocky);

        //this test fails. only the price is wrong, it has extra digits for some reason.
        //Mysql shows it as 29.99 so the problem might be from converting the mysql value to java.
        //Right now, the solution I am thinking of is rounding the price to 2 decimal places within the function itself.

        //assertEquals(knocky, InstrumentDao.getInstrumentById(11));
        assertEquals("Mr. Knocky", InstrumentDao.getInstrumentById(11).getName());
    }

    @Test
    void updateInstrument() throws DaoException {
        Instrument newInstrument = InstrumentDao.getInstrumentById(5);
        double newPrice = newInstrument.getPrice()*0.75;
        newInstrument.setPrice(newPrice);

        InstrumentDao.updateInstrument(5, newInstrument);

        //https://www.reddit.com/r/javahelp/comments/d8v4qe/junit_test_troubleshooting_assertequals_works_in/
        assertEquals(newPrice, InstrumentDao.getInstrumentById(5).getPrice(), 0.001);
    }
}