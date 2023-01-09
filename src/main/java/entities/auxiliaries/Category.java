package entities.auxiliaries;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class Category {
    private Long id;
    private String name;
}
