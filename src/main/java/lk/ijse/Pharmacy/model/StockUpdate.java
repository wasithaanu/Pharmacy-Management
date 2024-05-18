package lk.ijse.Pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StockUpdate {
    private String id;
    private double qty;
}
