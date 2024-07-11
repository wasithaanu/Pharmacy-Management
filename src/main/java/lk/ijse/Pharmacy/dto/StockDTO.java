package lk.ijse.Pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StockDTO {
    private String st_code;
    private String name;
    private double unit_price;
    private int qty;

}
