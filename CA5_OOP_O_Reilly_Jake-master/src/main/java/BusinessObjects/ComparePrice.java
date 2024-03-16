package BusinessObjects;

import DTOs.Instrument;

import java.util.Comparator;

public class ComparePrice implements Comparator<Instrument> {
    @Override
    public int compare(Instrument o1, Instrument o2) {
        return Double.compare(o1.getPrice(), o2.getPrice());
    }
}
