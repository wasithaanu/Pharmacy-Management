package lk.ijse.Pharmacy.bo.custom.impl;

import lk.ijse.Pharmacy.bo.custom.RegisterBO;
import lk.ijse.Pharmacy.dao.DAOFactory;
import lk.ijse.Pharmacy.dao.custom.AdminDAO;
import lk.ijse.Pharmacy.dto.AdminDTO;
import lk.ijse.Pharmacy.entity.Admin;
import lk.ijse.Pharmacy.entity.Customer;

import java.sql.SQLException;

public class RegisterBOImpl implements RegisterBO {
    AdminDAO adminDAO = (AdminDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ADMIN);

    @Override
    public boolean regAdmin(AdminDTO dto) throws SQLException, ClassNotFoundException {
        return adminDAO.add(new Admin(dto.getUsername(),dto.getPassword()));
    }
}
