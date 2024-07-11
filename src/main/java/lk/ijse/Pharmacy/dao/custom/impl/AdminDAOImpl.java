package lk.ijse.Pharmacy.dao.custom.impl;

import lk.ijse.Pharmacy.dao.SQLUtil;
import lk.ijse.Pharmacy.dao.custom.AdminDAO;
import lk.ijse.Pharmacy.entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public ArrayList<Admin> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Admin entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO admin VALUES(?,  ?)";
        return SQLUtil.execute(sql, entity.getUsername(), entity.getPassword() );
    }

    @Override
    public boolean update(Admin entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;

    }

    @Override
    public Admin search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT username, password FROM admin WHERE username = ?";
        ResultSet result = SQLUtil.execute(sql, id);

        if (result.next()) {
            return new Admin(

                    result.getString(1),
                    result.getString(2)

            );
        }
        return null;
    }


}
