package entities.auxiliaries;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class Inventory {
    private int sold;
    private int pending;
    private int available;
}
