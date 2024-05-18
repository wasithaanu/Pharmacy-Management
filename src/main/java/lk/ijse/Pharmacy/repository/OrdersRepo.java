package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepo {
    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(4);
            String date = resultSet.getString(3);
            String animal = resultSet.getString(1);
            String diseases = resultSet.getString(2);
            String cusId = resultSet.getString(5);
            String empId = resultSet.getString(6);

            Order order = new Order(id, date, animal, diseases,cusId,empId);
            orderList.add(order);
        }
        return orderList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM orders WHERE o_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, order.getAnimal());
        pstm.setObject(2, order.getDiseases());
        pstm.setObject(3, order.getDate());
        pstm.setObject(4, order.getId());
        pstm.setObject(5, order.getCusId());
        pstm.setObject(6, order.getEmpId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Order order) throws SQLException {
        String sql = "UPDATE orders SET type_of_animal = ?, animal_diseases = ?, o_date = ?, cu_id = ?, e_id = ? WHERE o_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, order.getAnimal());
        pstm.setObject(2, order.getDiseases());
        pstm.setObject(3, order.getDate());
        pstm.setObject(4, order.getId());
        pstm.setObject(3, order.getCusId());
        pstm.setObject(4, order.getEmpId());

        return pstm.executeUpdate() > 0;
    }

    public static Order searchById(String id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE o_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Order order = null;

        if (resultSet.next()) {
            String type_of_animal = resultSet.getString(1);
            String animal_diseases = resultSet.getString(2);
            String o_date = resultSet.getString(3);
            String o_id = resultSet.getString(4);
            String cu_id = resultSet.getString(5);
            String e_id = resultSet.getString(6);

            order = new Order(type_of_animal, animal_diseases, o_date, o_id,cu_id,e_id);
        }
        return order;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT o_id FROM orders ORDER BY o_id desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<String> getOIds() throws SQLException {

        String sql = "SELECT o_id FROM product";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT o_id FROM orders";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
