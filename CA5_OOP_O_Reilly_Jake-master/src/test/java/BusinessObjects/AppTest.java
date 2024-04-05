package BusinessObjects;

import DAOs.InstrumentDaoInterface;
import DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Exceptions.DaoException;
import org.junit.jupiter.api.Test;

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
}