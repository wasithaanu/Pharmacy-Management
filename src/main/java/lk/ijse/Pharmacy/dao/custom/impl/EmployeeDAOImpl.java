package lk.ijse.Pharmacy.dao.custom.impl;

import lk.ijse.Pharmacy.dao.SQLUtil;
import lk.ijse.Pharmacy.dao.custom.EmployeeDAO;

import lk.ijse.Pharmacy.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        while (rst.next()) {
            Employee employee =  new Employee(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5));
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?,?)";
        return SQLUtil.execute(sql, entity.getE_id(), entity.getE_name(), entity.getIn_time(),
                entity.getOut_time(),entity.getDate());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        String sql ="UPDATE employee SET e_name = ?, in_time = ?, out_time = ? ,date = ? WHERE e_id = ?";
        return SQLUtil.execute(sql,entity.getE_name(),entity.getIn_time(),entity.getOut_time(),entity.getDate(),entity.getE_id());
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        String em_id;
        ResultSet Set = SQLUtil.execute("SELECT e_id FROM employee ORDER BY e_id desc LIMIT 1");
        if (Set.next()) {
            String[] E00 = Set.getString(1).split("E00");
            int id = Integer.parseInt(E00[1]);
            id++;
            em_id = "E00" + id;


        } else {
            em_id = "E001";

        }
        return em_id;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM employee WHERE e_name = ?";
        return SQLUtil.execute(sql,id);
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
