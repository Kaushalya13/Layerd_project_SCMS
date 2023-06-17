package lk.ijse.scms.dao;

import lk.ijse.scms.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,VEHICLE,EMPLOYEE,CUSTOMER,ITEM,SUPPLIER,COMPANY,ORDER,ORDERDETAILS
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case COMPANY:
                return new CompanyDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            default:
                return null;
        }
    }
}
