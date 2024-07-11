package lk.ijse.Pharmacy.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerTm {
        private String cu_id;
        private String cu_name;
        private String cu_address;
        private Integer cu_contact;
}
