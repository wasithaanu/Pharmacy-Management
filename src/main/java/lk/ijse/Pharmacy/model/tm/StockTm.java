package lk.ijse.Pharmacy.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StockTm {
    private String id;
    private String name;
    private Double unitPrice;
    private Integer qty;

}
