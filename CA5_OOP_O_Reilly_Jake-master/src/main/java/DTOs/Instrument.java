package DTOs;

// File by Jake O'Reilly

public class Instrument {

    // POJO Instrument following the format of the SQL:
    /*
        int id,
        String name,
        double price,
        String type
    */

    private int id;
    private String name;
    private double price; // Doubles are more precise than floats, so it will definitely hold the right values.
    private String type;

    // Full constructor
    public Instrument(int id, String name, double price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Empty constructor
    public Instrument() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
