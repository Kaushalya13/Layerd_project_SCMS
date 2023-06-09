package lk.ijse.scms.bo;

import lk.ijse.scms.bo.custom.impl.UserBOImpl;
import lk.ijse.scms.bo.custom.impl.VehicleBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER,VEHICLE
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case USER:
                return new UserBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
//            case PO:
//                return new PurchaseOrderBOImpl();
            default:
                return null;
        }
    }
}
