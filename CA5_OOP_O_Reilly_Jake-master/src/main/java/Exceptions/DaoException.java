package Exceptions;

import java.sql.SQLException;

// File by Jake O'Reilly

public class DaoException extends SQLException {

    public DaoException(String aMessage) {
        super(aMessage);
    }

}
