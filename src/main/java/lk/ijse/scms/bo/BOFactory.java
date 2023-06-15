package lk.ijse.scms.bo;

import lk.ijse.scms.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        USER,VEHICLE,EMPLOYEE,CUSTOMER,ITEM,SUPPLIER,COMPANY
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case USER:
                return new UserBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case COMPANY:
                return new CompanyBOImpl();
            default:
                return null;
        }
    }
}
