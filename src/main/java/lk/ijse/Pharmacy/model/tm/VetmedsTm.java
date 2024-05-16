package lk.ijse.Pharmacy.model.tm;
import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class VetmedsTm {
    private String code;
    private String oId;
    private String itemCode;
    private String name;
    private String desc;
    private double unitPrice;
    private int qty;
    private double amount;
    private JFXButton btnRemove;
}
