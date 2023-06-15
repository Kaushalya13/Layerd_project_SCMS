package lk.ijse.scms.dao.custom;

import lk.ijse.scms.dao.CrudDAO;
import lk.ijse.scms.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    public ArrayList<String> loadComboBox() throws SQLException;

    public ArrayList<String> loadCompanyId() throws SQLException;
}
