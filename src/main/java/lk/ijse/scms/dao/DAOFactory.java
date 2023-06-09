package lk.ijse.scms.dao;

import lk.ijse.scms.dao.custom.impl.UserDAOImpl;
import lk.ijse.scms.dao.custom.impl.VehicleDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,VEHICLE
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
//            case ORDER:
//                return new OrderDAOImpl();
//            case ORDER_DETAILS:
//                return new OrderDetailsDAOImpl();
//            case QUERY_DAO:
//                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
