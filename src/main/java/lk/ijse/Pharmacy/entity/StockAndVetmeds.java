package lk.ijse.Pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockAndVetmeds {
    private StockUpdate stockUpdate;
    private List<VetmedDetails> vetmedDetails;
}
