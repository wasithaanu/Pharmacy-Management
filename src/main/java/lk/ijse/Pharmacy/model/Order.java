package lk.ijse.Pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Order {
    private String id;
    private String date;
    private String animal;
    private String diseases;
    private String cusId;
    private String empId;
}
