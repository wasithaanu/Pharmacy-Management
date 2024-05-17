package lk.ijse.Pharmacy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VetmedDetails {
    private String code;
    private String name;
    private String desc;
    private double unitPrice;
    private String itemCode;
    private double amount;
    private int qty;
    private String oId;
    private String date;
}
