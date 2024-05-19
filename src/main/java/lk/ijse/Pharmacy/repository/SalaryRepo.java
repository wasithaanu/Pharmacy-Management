package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Salary;
import lk.ijse.Pharmacy.model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {
    public static List<Salary> getAll() throws SQLException {

            String sql = "SELECT * FROM salary";

            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            List<Salary> salaryList = new ArrayList<>();
            while (resultSet.next()) {
                String saId = resultSet.getString(1);
                String emId = resultSet.getString(2);
                String name = resultSet.getString(3);
                String month = resultSet.getString(4);
                String date = resultSet.getString(5);
                String amount = resultSet.getString(5);


                Salary salary = new Salary(saId, emId, name, month, date, amount);
                salaryList.add(salary);
            }
            return salaryList;
        }


    public static Salary searchById(String saId) throws SQLException {
        String sql = "SELECT * FROM salary WHERE sa_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, saId);
        ResultSet resultSet = pstm.executeQuery();

        Salary salary = null;

        if (resultSet.next()) {
            String sid = resultSet.getString(1);
            String emId = resultSet.getString(2);
            String name = resultSet.getString(3);
            String month = resultSet.getString(4);
            String amount = resultSet.getString(5);
            String  date= resultSet.getString(6);

            salary = new Salary(sid, emId, name, month, amount, date);
        }
        return salary;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT sa_id FROM salary ORDER BY sa_id desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Salary salary) throws SQLException {
        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, salary.getSaId());
        pstm.setObject(2, salary.getEmId());
        pstm.setObject(3, salary.getName());
        pstm.setObject(4, salary.getMonth());
        pstm.setObject(5, salary.getAmount());
        pstm.setObject(6, salary.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Salary salary) throws SQLException {
        String sql = "UPDATE salary SET sa_id = ?, em_id = ?, name = ?, month = ?, date = ?, amount = ?, WHERE sa_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, salary.getSaId());
        pstm.setObject(2, salary.getEmId());
        pstm.setObject(3, salary.getName());
        pstm.setObject(4, salary.getMonth());
        pstm.setObject(4, salary.getDate());
        pstm.setObject(4, salary.getAmount());

        return pstm.executeUpdate() > 0;
    }
}
    

