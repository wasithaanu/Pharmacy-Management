package lk.ijse.Pharmacy.model.tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class  SalaryTm {
    private String saId;
    private String emId;
    private String name;
    private String month;
    private String date;
    private String amount;

}
