package DAOs;

import Server.DAOs.InstrumentDaoInterface;
import Server.DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Server.Exceptions.DaoException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

class MySqlInstrumentDaoTest {
    InstrumentDaoInterface InstrumentDao = new MySqlInstrumentDao();

    //Reset Database with SQL to test

    @Test
    //@Order(1) can't get this test to run first and don't know how to test it without running it first.
    void getAllInstruments() throws DaoException {
        List<Instrument> instruments = InstrumentDao.getAllInstruments();
        assertEquals(10, instruments.size());
    }

    // get an existing instrument
    @Test
    void getInstrumentById1() throws DaoException {

        //this function appears to run before the delete for whatever reason. made the ids different
        Instrument instrument = InstrumentDao.getInstrumentById(8);
        assertEquals(8, instrument.getId());
        assertEquals("Casio SA 50 Mini Portable Keyboard", instrument.getName());
        assertEquals(61.20, instrument.getPrice(), 0.001);
        assertEquals("PIANO", instrument.getType());
    }

    // get instrument with an id that isn't registered
    @Test
    void getInstrumentById2() throws DaoException {
        Instrument instrument = InstrumentDao.getInstrumentById(0);
        assertEquals(0, instrument.getId());
        assertEquals(null, instrument.getName());
        assertEquals(0, instrument.getPrice(), 0.1);
        assertEquals(null, instrument.getType());
    }

    // positive id
    @Test
    void insertInstrument1() throws DaoException {
        Instrument knocky = new Instrument(11, "Mr. Knocky", 29.99, "DRUM");
        InstrumentDao.insertInstrument(knocky);

        //assertEquals(knocky, InstrumentDao.getInstrumentById(11));
        Instrument retrieved = InstrumentDao.getInstrumentById(11);

        assertEquals(11, retrieved.getId());
        assertEquals("Mr. Knocky", retrieved.getName());
        assertEquals(29.99, retrieved.getPrice(), 0.001);
        assertEquals("DRUM", retrieved.getType());
    }

    // negative id
    // test fails because a primary key in a database can't be negative
    @Test
    void insertInstrument2() throws DaoException {
        Instrument recorder = new Instrument(-1, "Recorder", 8.50, "WIND");
        InstrumentDao.insertInstrument(recorder);

        Instrument retrieved = InstrumentDao.getInstrumentById(-1);

        assertEquals(0, retrieved.getId());
        assertEquals(null, retrieved.getName());
        assertEquals(0, retrieved.getPrice(), 0.001);
        assertEquals(null, retrieved.getType());
    }

    @Test
    void deleteInstrumentById1() throws DaoException {
        InstrumentDao.deleteInstrumentById(7);
        Instrument instrument = InstrumentDao.getInstrumentById(7);
        //assertEquals(null, instrument);
        assertEquals(0, instrument.getId());
        assertEquals(null, instrument.getName());
        assertEquals(0, instrument.getPrice(), 0.1);
        assertEquals(null, instrument.getType());

        //List<Instrument> instruments = InstrumentDao.getAllInstruments();
        //assertEquals(9, instruments.size());  //fix
    }

    @Test
    void deleteInstrumentById2() throws DaoException {
        InstrumentDao.deleteInstrumentById(73);
        Instrument instrument = InstrumentDao.getInstrumentById(73);
        //assertEquals(null, instrument);
        assertEquals(0, instrument.getId());
        assertEquals(null, instrument.getName());
        assertEquals(0, instrument.getPrice(), 0.1);
        assertEquals(null, instrument.getType());

        //List<Instrument> instruments = InstrumentDao.getAllInstruments();
        //assertEquals(9, instruments.size());  //fix
    }

    @Test
    void updateInstrument1() throws DaoException {
        Instrument newInstrument = InstrumentDao.getInstrumentById(5);

        newInstrument.setPrice(100);

        InstrumentDao.updateInstrument(5, newInstrument);

        //Roland A-49 MIDI Controller Keyboard, White', 154.75, 'PIANO

        Instrument retrieved = InstrumentDao.getInstrumentById(5);

        assertEquals(5, retrieved.getId());
        assertEquals("Roland A-49 MIDI Controller Keyboard, White", retrieved.getName());
        assertEquals(100, retrieved.getPrice(), 0.001); //https://www.reddit.com/r/javahelp/comments/d8v4qe/junit_test_troubleshooting_assertequals_works_in/
        assertEquals("PIANO", retrieved.getType());
    }

    // updating an instrument that doesn't exist
    // it doesn't update anything
    @Test
    void updateInstrument2() throws DaoException {
        Instrument newInstrument = InstrumentDao.getInstrumentById(5);

        newInstrument.setPrice(100);

        InstrumentDao.updateInstrument(22, newInstrument);

        Instrument retrieved = InstrumentDao.getInstrumentById(0);

        assertEquals(0, retrieved.getId());
        assertEquals(null, retrieved.getName());
        assertEquals(0, retrieved.getPrice(), 0.001);
        assertEquals(null, retrieved.getType());
    }

    // updating an existing instrument's id to be the same as another instruments because I'm curious.
    /*@Test
    void updateInstrument3() throws DaoException {
        Instrument newInstrument = InstrumentDao.getInstrumentById(2);

        newInstrument.setId(3);

        InstrumentDao.updateInstrument(2, newInstrument);

        Instrument retrieved = InstrumentDao.getInstrumentById(3);

        //Epiphone Les Paul Muse, Purple Passion Metallic', 468.00, 'GUITAR

        assertEquals(3, retrieved.getId());
        assertEquals("Epiphone Les Paul Muse, Purple Passion Metallic", retrieved.getName());
        assertEquals(468.00, retrieved.getPrice(), 0.001);
        assertEquals("GUITAR", retrieved.getType());
    }*/
}