package lk.ijse.scms.dao.custom;

import lk.ijse.scms.dao.CrudDAO;
import lk.ijse.scms.dao.SuperDAO;
import lk.ijse.scms.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Orders> {
    public ArrayList<String> loadItemCode() throws SQLException;

    public ArrayList<String> loadCustomerId() throws SQLException;
}
