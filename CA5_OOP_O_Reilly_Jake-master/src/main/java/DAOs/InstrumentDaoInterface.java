package DAOs;

// File by Jake O'Reilly

import BusinessObjects.ComparePrice;
import DTOs.Instrument;
import Exceptions.DaoException;

import java.util.Comparator;
import java.util.List;

public interface InstrumentDaoInterface {

    public List<Instrument> getAllInstruments() throws DaoException;

    public Instrument getInstrumentById(int id) throws DaoException;

    public void deleteInstrumentById(int id) throws DaoException;

    public void insertInstrument(Instrument i) throws DaoException;
    public void updateInstrument(int id, Instrument i) throws DaoException;

}
