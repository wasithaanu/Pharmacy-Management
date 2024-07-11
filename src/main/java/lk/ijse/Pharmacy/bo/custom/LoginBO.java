package lk.ijse.Pharmacy.bo.custom;

import lk.ijse.Pharmacy.bo.SuperBO;
import lk.ijse.Pharmacy.dto.AdminDTO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {

    AdminDTO login(String id)throws SQLException, ClassNotFoundException;
}
