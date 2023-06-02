
package org.apache.fineract.infrastructure.hooks.data;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
@SuppressWarnings("unused")
@Data
public class Grouping implements Serializable {
    private String name;
    private List<Entity> entities;
}
