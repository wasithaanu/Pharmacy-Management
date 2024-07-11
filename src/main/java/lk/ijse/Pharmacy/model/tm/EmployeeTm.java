package lk.ijse.Pharmacy.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

 public class EmployeeTm {
   private String e_id;
   private String e_name;
   private String in_time;
   private String out_time;
   private String date;

}
