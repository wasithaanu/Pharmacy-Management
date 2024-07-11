package lk.ijse.Pharmacy.bo;

import lk.ijse.Pharmacy.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,LOGIN,REGISTER,DASHBORD,EMPlOYEE,OEDER,PLACEORDER,SALARY,STOCK,SUPPLIER,VETMEDS
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case LOGIN:
                return new LoginBoImpl();
            case REGISTER:
                return new RegisterBOImpl();
            case DASHBORD:
                return new DashboardBOimpl();
            case VETMEDS:
                return new VetmedsBOImpl();
            case EMPlOYEE:
                return new EmployeeBOImpl();

            default:
                return null;
        }
    }

}
