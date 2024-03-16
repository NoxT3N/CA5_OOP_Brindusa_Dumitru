package BusinessObjects;

// File started by Jake O'Reilly

import DAOs.InstrumentDaoInterface;
import DAOs.MySqlInstrumentDao;
import DTOs.Instrument;
import Exceptions.DaoException;

import java.util.*;

public class App {
    public static void main(String[] args) throws DaoException {
        App app = new App();
        app.start();
    }

    static int userInput = -1;
    static Scanner keyboard = new Scanner(System.in);

    InstrumentDaoInterface InstrumentDao = new MySqlInstrumentDao();

    // Start app
    public void start() throws DaoException {

        System.out.println("App start, welcome!");

        // Menu printing system, show app commands
        do {

            System.out.println("\nSelect a command:\n" +
                    "[1] Get all instruments\n" +
                    "[2] Get instrument by name (id)\n" +
                    "[3] Delete instrument by name (id)\n" +
                    "[4] Insert an instrument\n" +
                    "[5] Update an existing entity by id\n" +
                    "[6] Filter instruments by price (asc)\n" +
                    "[0] Exit\n");
            System.out.printf("Enter: ");

            userInput = keyboard.nextInt();

            System.out.println();
            int id;

            switch (userInput) {
                case 1:
                    //IInstrumentDao.deleteInstrumentById("hi");
                    //System.out.println(InstrumentDao.getAllInstruments());
                    List<Instrument> instruments = InstrumentDao.getAllInstruments();

                    printInstruments(instruments);

                    break;
                case 2:
                    System.out.printf("Enter id: ");
                    id = keyboard.nextInt();

                    InstrumentDao.getInstrumentById(id);
                    break;
                case 3:
                    System.out.println("Enter id: ");
                    id = keyboard.nextInt();

                    InstrumentDao.deleteInstrumentById(id);
                    break;
                case 4:
                    keyboard.nextLine();
                    System.out.println("Enter name: ");
                    String name = keyboard.nextLine();
                    System.out.println("Enter type: ");
                    String type = keyboard.nextLine();
                    System.out.println("Enter price: ");
                    double price = keyboard.nextDouble();

                    InstrumentDao.insertInstrument(new Instrument(0,name,price,type));
                    break;
                case 5:
                    System.out.println("Enter id: ");
                    id = keyboard.nextInt();
                    Instrument i = InstrumentDao.getInstrumentById(id);
                    int choice;

                    do {
                        System.out.println("Choose what field you wish to update");
                        System.out.println("[1] name");
                        System.out.println("[2] type");
                        System.out.println("[3] price");
                        System.out.println("[0] apply changes and exit");

                        while(!keyboard.hasNextInt()){
                            System.out.println("Please enter a valid number");
                            keyboard.next();
                        }
                        choice = keyboard.nextInt();
                        keyboard.nextLine();

                        switch (choice){
                            case 1:
                                System.out.println("Enter new name:");
                                name = keyboard.nextLine();
                                i.setName(name);
                                System.out.println("Name has been set to: "+name);
                                break;
                            case 2:
                                System.out.println("Enter new type:");
                                type = keyboard.nextLine();
                                i.setType(type);
                                System.out.println("Type has been set to: "+type);
                                break;
                            case 3:
                                System.out.println("Enter new price:");
                                while(!keyboard.hasNextDouble()){
                                    System.out.println("Please enter a decimal number");
                                    keyboard.next();
                                }
                                price = keyboard.nextDouble();
                                i.setPrice(price);
                                System.out.println("Price has been set to: "+price);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Please enter a valid number");
                        }
                    }while(choice != 0);

                    InstrumentDao.updateInstrument(id,i);

                    break;
                case 6:
                    ComparePrice cp = new ComparePrice();

                    printInstruments(findPlayersUsingFilter(cp));

                    break;
            }

        } while (userInput != 0);

        System.out.println("Exiting app, goodbye!");

    }

    public void printInstruments(List<Instrument> instruments) {
        for (Instrument i : instruments) {
            System.out.println(i.toString());
        }
    }

    public List<Instrument> findPlayersUsingFilter(Comparator cp) throws DaoException {
        List<Instrument> filteredInstruments = InstrumentDao.getAllInstruments();

        Collections.sort(filteredInstruments, cp);

        return filteredInstruments;
    }

}
