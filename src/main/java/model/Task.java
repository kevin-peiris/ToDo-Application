package model;

import com.mysql.cj.conf.BooleanProperty;
import javafx.beans.property.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    private String tskId;
    private LocalDate tskDate;
    private String tskTitle;
    private String tskDescription;

}
