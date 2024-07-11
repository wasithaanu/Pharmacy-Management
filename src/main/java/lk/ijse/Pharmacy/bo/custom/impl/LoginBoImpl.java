package lk.ijse.Pharmacy.bo.custom.impl;

import lk.ijse.Pharmacy.bo.custom.LoginBO;
import lk.ijse.Pharmacy.dao.DAOFactory;
import lk.ijse.Pharmacy.dao.custom.AdminDAO;
import lk.ijse.Pharmacy.dto.AdminDTO;
import lk.ijse.Pharmacy.entity.Admin;

import java.sql.SQLException;

public class LoginBoImpl implements LoginBO {

    AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADMIN);

    @Override
    public AdminDTO login(String id) throws SQLException, ClassNotFoundException {
        Admin admin= adminDAO.search(id);
        return new AdminDTO(admin.getUsername(),admin.getPassword());
    }
}
