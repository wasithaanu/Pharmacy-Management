package lk.ijse.Pharmacy.bo.custom.impl;
import lk.ijse.Pharmacy.bo.custom.CustomerBO;
import lk.ijse.Pharmacy.dao.DAOFactory;
import lk.ijse.Pharmacy.dao.custom.CustomerDAO;
import lk.ijse.Pharmacy.dto.CustomerDTO;
import lk.ijse.Pharmacy.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getCu_id(),dto.getCu_name(),dto.getCu_address(),dto.getCu_contact()));
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCu_id(),c.getCu_name(),c.getCu_address(), c.getCu_contact()));
        }
        return allCustomers;
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCu_id(),dto.getCu_name(),dto.getCu_address(),dto.getCu_contact()));
    }

    @Override
    public boolean deleteCustomer(String cu_name) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(cu_name);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }



}
