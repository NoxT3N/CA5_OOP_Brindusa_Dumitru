package Server.DAOs;

// File by Jake O'Reilly

import DTOs.Instrument;
import Server.Exceptions.DaoException;

import java.util.List;

public interface InstrumentDaoInterface {

    List<Instrument> getAllInstruments() throws DaoException;

    Instrument getInstrumentById(int id) throws DaoException;

    void deleteInstrumentById(int id) throws DaoException;

    void insertInstrument(Instrument i) throws DaoException;
    void updateInstrument(int id, Instrument i) throws DaoException;

}
