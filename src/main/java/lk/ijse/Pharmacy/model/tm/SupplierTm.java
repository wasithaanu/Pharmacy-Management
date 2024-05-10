package lk.ijse.Pharmacy.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupplierTm {
    private String  id;
    private String name;
    private String address;
    private Integer contact;
}
