package lk.ijse.Pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private String id;
    private String name;
    private String inTime;
    private String outTime;
    private String date;
}
