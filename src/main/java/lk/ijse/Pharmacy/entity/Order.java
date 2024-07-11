package lk.ijse.Pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Order {
    private String o_id;
    private String cu_id;
    private String e_id;
    private String o_date;
    private String animal_diseases;
    private String type_of_animal;

}
