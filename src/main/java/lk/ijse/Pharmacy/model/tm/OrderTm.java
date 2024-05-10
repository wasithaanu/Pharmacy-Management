package lk.ijse.Pharmacy.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderTm {
    private String id;
    private String date;
    private String animal;
    private String diseases;
    private String cusId;
    private String empId;
}
