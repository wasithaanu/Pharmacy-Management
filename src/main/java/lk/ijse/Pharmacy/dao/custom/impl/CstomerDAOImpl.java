package lk.ijse.Pharmacy.dao.custom.impl;



import lk.ijse.Pharmacy.dao.SQLUtil;
import lk.ijse.Pharmacy.dao.custom.CustomerDAO;
import lk.ijse.Pharmacy.entity.Customer;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CstomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?)";
        return SQLUtil.execute(sql, entity.getCu_id(), entity.getCu_name(), entity.getCu_address(),
                entity.getCu_contact());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

        String sql ="Update customer set  cu_name = ?, cu_address = ?, cu_contact = ? WHERE cu_id = ?";
        return SQLUtil.execute(sql,entity.getCu_name(),entity.getCu_address(),entity.getCu_contact(),entity.getCu_id()
        );
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        String cusiD;
        ResultSet Set = SQLUtil.execute("SELECT cu_id FROM customer ORDER BY cu_id  DESC LIMIT 1");
        if (Set.next()) {
            String[] C00 = Set.getString(1).split("C00");
            int id = Integer.parseInt(C00[1]);
            id++;
            cusiD = "C00" + id;


        } else {
            cusiD = "C001";

        }
        return cusiD;
    }

    @Override
    public boolean delete(String name) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE cu_name = ?";
        return SQLUtil.execute(sql,name);

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

}
