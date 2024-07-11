package lk.ijse.Pharmacy.bo.custom;

import lk.ijse.Pharmacy.bo.SuperBO;
import lk.ijse.Pharmacy.dto.CustomerDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;
    boolean deleteCustomer(String cu_name) throws SQLException, ClassNotFoundException ;
    String generateNewCustomerID() throws SQLException, ClassNotFoundException;

}
