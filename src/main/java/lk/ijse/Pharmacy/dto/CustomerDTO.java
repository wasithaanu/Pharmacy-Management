package lk.ijse.Pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerDTO {
    private String cu_id;
    private String cu_name;
    private String cu_address;
    private Integer cu_contact;


    @Override
    public String toString() {
        return "CustomerDTO{" +
                "cu_id='" + cu_id + '\'' +
                ", cu_name='" + cu_name + '\'' +
                ", cu_address='" + cu_address + '\'' +
                ", cu_contact=" + cu_contact +
                '}';
    }
}
