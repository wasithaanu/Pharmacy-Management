package lk.ijse.Pharmacy.bo.custom;

import lk.ijse.Pharmacy.bo.SuperBO;
import lk.ijse.Pharmacy.dto.AdminDTO;
import lk.ijse.Pharmacy.dto.CustomerDTO;

import java.sql.SQLException;

public interface RegisterBO extends SuperBO {
    boolean regAdmin(AdminDTO dto) throws SQLException, ClassNotFoundException ;
}
