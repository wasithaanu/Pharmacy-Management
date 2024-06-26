package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Stock;
import lk.ijse.Pharmacy.model.StockUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static List<Stock> getAll() throws SQLException {
        String sql = "SELECT * FROM stock";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Stock> stockList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            Double unitPrice = Double.valueOf(resultSet.getString(3));
            Integer qty = Integer.valueOf(resultSet.getString(4));

            Stock stock = new Stock(id, name, unitPrice, qty);
            stockList.add(stock);
        }
        return stockList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM stock WHERE st_code = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, stock.getId());
        pstm.setObject(2, stock.getName());
        pstm.setObject(3, stock.getUnitPrice());
        pstm.setObject(4, stock.getQty());

        return pstm.executeUpdate() > 0;
    }

    private static boolean save(StockUpdate stockUpdate) throws SQLException {
            if(!update(stockUpdate)) {
                return false;
            }
        return true;
    }

    public static boolean update(StockUpdate stockUpdate) throws SQLException {
        String sql = "UPDATE stock SET qty= qty -? WHERE st_code=?"                   ;
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, stockUpdate.getQty());
        pstm.setObject(2, stockUpdate.getId());
        System.out.println("ehema puluwnd?");
        return false;
    }

    public static boolean update(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET name = ?, unit_price = ?, qty = ? WHERE st_code = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, stock.getName());
        pstm.setObject(2, stock.getUnitPrice());
        pstm.setObject(3, stock.getQty());
        pstm.setObject(4, stock.getId());

        return pstm.executeUpdate() > 0;
    }

    public static Stock searchById(String id) throws SQLException {
        String sql = "SELECT * FROM stock WHERE st_code = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Stock stock = null;

        if (resultSet.next()) {
            String st_code = resultSet.getString(1);
            String name = resultSet.getString(2);
            Double unit_price = Double.valueOf(resultSet.getString(3));
            Integer qty = Integer.valueOf(resultSet.getString(4));

            stock = new Stock(st_code, name, unit_price, qty);
        }
        return stock;
    }

    public static Stock searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM stock WHERE name = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Stock(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    public static List<String> getName() throws SQLException {
        String sql = "SELECT  name FROM stock";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT v_code FROM vetmeds ORDER BY v_code desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;

    }
}

