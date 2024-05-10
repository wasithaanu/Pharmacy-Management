package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supplierList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            Integer contact = Integer.valueOf(resultSet.getString(4));

            Supplier supplier = new Supplier(id, name, address, contact);
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE su_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, supplier.getId());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getAddress());
        pstm.setObject(4, supplier.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET su_name = ?, su_address = ?, su_contact = ? WHERE su_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, supplier.getId());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getAddress());
        pstm.setObject(4, supplier.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE su_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Supplier supplier = null;

        if (resultSet.next()) {
            String su_id = resultSet.getString(1);
            String su_name = resultSet.getString(2);
            String su_address = resultSet.getString(3);
            Integer su_contact = Integer.valueOf(resultSet.getString(4));

            supplier = new Supplier(su_id, su_name, su_address, su_contact);
        }
        return supplier;
    }
}
