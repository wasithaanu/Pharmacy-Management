package lk.ijse.Pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Stock {
    private String id;
    private String name;
    private double unitPrice;
    private int qty;

}
