package lk.ijse.Pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Stock {
    private String st_code;
    private String name;
    private double unit_price;
    private int qty;

}
