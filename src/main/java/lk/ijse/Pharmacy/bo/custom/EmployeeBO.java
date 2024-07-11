package lk.ijse.Pharmacy.bo.custom;
import lk.ijse.Pharmacy.bo.SuperBO;
import lk.ijse.Pharmacy.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;
    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException ;
    boolean deleteEmployee(String e_name) throws SQLException, ClassNotFoundException ;
    String generateNewEmployeeID() throws SQLException, ClassNotFoundException;
}
