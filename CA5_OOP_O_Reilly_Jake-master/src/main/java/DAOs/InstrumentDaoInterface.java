package DAOs;

// File by Jake O'Reilly

import DTOs.Instrument;
import Exceptions.DaoException;
import java.util.List;

public interface InstrumentDaoInterface {

    public List<Instrument> getAllInstruments() throws DaoException;

    public Instrument getInstrumentById(int id) throws DaoException;

    public void deleteInstrumentById(String id) throws DaoException;

    public Instrument insertInstrument(Instrument i) throws DaoException;

}
