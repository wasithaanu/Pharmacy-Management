package lk.ijse.Pharmacy.repository;

import lk.ijse.Pharmacy.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    }

