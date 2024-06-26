package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String inTime = resultSet.getString(3);
            String outTime = resultSet.getString(4);
            String date = resultSet.getString(5);

            Employee employee = new Employee(id, name, inTime, outTime,date);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getInTime());
        pstm.setObject(4, employee.getOutTime());
        pstm.setObject(5, employee.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET e_name = ?, in_time = ?, out_time = ? ,date = ? WHERE e_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getInTime());
        pstm.setObject(4, employee.getOutTime());
        pstm.setObject(5, employee.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static Employee searchById(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE e_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String e_id = resultSet.getString(1);
            String e_name = resultSet.getString(2);
            String in_time = resultSet.getString(3);
            String out_time = resultSet.getString(4);
            String date = resultSet.getString(5);

            employee = new Employee(e_id, e_name, in_time, out_time,date);
        }
        return employee;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE e_name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT e_id FROM employee ORDER BY e_id desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

}
