package lk.ijse.Pharmacy.dao;

import lk.ijse.Pharmacy.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER, ADMIN, EMPLOYEE
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CstomerDAOImpl() ;
            case ADMIN:
                return new AdminDAOImpl() ;
            case EMPLOYEE:
                return new EmployeeDAOImpl() ;

            default:
                return null;
        }
    }


}
