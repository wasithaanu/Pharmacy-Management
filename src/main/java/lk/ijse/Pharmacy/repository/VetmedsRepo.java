package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.model.VetmedDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VetmedsRepo {
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

    public static boolean save(List<VetmedDetails> vdList) throws SQLException {
        for (VetmedDetails od : vdList) {
            if (!save(od)) {
                return false;
            }
        }
        return true;
    }
    public static boolean save(VetmedDetails od) throws SQLException {
        String sql = "INSERT INTO vetmeds VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,od.getCode());
        pstm.setObject(2,od.getName());
        pstm.setObject(3,od.getDesc());
        pstm.setObject(4,od.getUnitPrice());
        pstm.setObject(5,od.getItemCode());
        pstm.setObject(6,od.getAmount());
        pstm.setObject(7,od.getQty());
        pstm.setObject(8,od.getOId());
        pstm.setObject(9,od.getDate());

        return pstm.executeUpdate(sql) >-1;
    }
}

