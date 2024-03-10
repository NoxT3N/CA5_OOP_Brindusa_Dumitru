package DAOs;

// File started by Jake O'Reilly

import DTOs.Instrument;
import Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlInstrumentDao extends MySqlDao implements InstrumentDaoInterface {

    // Feature 1 – Get all Entities (assuming Player entities in this example)
    // e.g. getAllPlayers() - return a List of all the entities and display the returned list.
    // Ciara
    @Override
    public List<Instrument> getAllInstruments() throws DaoException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        List<Instrument> instrumentList = new ArrayList<>();

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM instruments";
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getFloat("price");
                String type = resultSet.getString("type");

                instrumentList.add(new Instrument(id, name, price, type));
            }
        }
        catch (SQLException e) {
            throw new DaoException("getAllInstruments() " + e.getMessage());
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    freeConnection(conn);
                }
            }
            catch (SQLException e) {
                throw new DaoException("getAllInstruments() " + e.getMessage());
            }
        }

        for (Instrument i : instrumentList) {
            System.out.println(i.toString());
        }

        return instrumentList;
    }

    // Feature 2 – Find and Display (a single) Entity by Key
    // e.g. getPlayerById(id ) – return a single entity (DTO) and display its contents.
    // Ciara
    @Override
    public Instrument getInstrumentById(int id) throws DaoException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Instrument instrument = new Instrument();

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM instruments WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getFloat("price");
                String type = resultSet.getString("type");

                instrument = new Instrument(id, name, price, type);
            }
        }
        catch (SQLException e) {
            throw new DaoException("getInstrumentById() " + e.getMessage());
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    freeConnection(conn);
                }
            }
            catch (SQLException e) {
                throw new DaoException("getInstrumentById() " + e.getMessage());
            }
        }


        //print
        System.out.println(instrument.toString());

        return instrument;
    }

    // Feature 3 – Delete an Entity by key
    // e.g. deletePlayerById(id) – remove specified entity from database
    // Felix
    @Override
    public void deleteInstrumentById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = this.getConnection();

            String query = "DELETE FROM instruments WHERE id = "+ id;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("insturment deletion error:  " + e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("instrument delete error: " + e.getMessage());
            }
        }

        System.out.println("Instrument deleted from the database");
    }

    // Feature 4 – Insert an Entity
    // (gather data, instantiate a Player object, pass into DAO method for insertion in DB)
    // e.g. Player insertPlayer(Player p)
    // return new entity (Player DTO) that includes the assigned auto-id.
    // Felix
    @Override
    public void insertInstrument(Instrument i) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = this.getConnection();
            String query = "INSERT INTO Instruments (name, price, type) VALUES (?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,i.getName());
            preparedStatement.setDouble(2,i.getPrice());
            preparedStatement.setString(3,i.getType());

            preparedStatement.executeUpdate();

        }
        catch(SQLException e){
            throw new DaoException("instrument insertion error: "+ e.getMessage());
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("instrument insertion error: " + e.getMessage());
            }
        }

        System.out.println("Instrument has been succesfully added to the database");

    }
}
