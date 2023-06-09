package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.VehicleDAO;
import lk.ijse.scms.entity.User;
import lk.ijse.scms.entity.Vehicle;
import lk.ijse.scms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {

    @Override
    public ArrayList<Vehicle> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> allVehicle = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Vehicle");
        while (rst.next()) {
            Vehicle vehicle = new Vehicle(rst.getString("vehicle_id"),rst.getString("vehicle_name"),rst.getString("vehicle_type"),rst.getString("customer_id"),rst.getString("company_id"),rst.getString("receive_date"),rst.getString("return_date"),rst.getString("status"));
            allVehicle.add(vehicle);
        }
        return allVehicle;
    }

    @Override
    public boolean add(Vehicle dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Vehicle (vehicle_id,vehicle_name,vehicle_type,customer_id,company_id,receive_date,return_date,status) VALUES (?,?,?,?,?,?,?,?)",dto.getVehicle_id(),dto.getVehicle_name(),dto.getVehicle_type(),dto.getCustomer_id(),dto.getCompany_id(),dto.getReceive_date(),dto.getReturn_date(),dto.getStatus());
    }

    @Override
    public boolean update(Vehicle dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Vehicle SET vehicle_name = ?,vehicle_type =? ,customer_id = ?,company_id = ?,receive_date = ?,return_date = ?,status = ?,vehicle_id = ?",dto.getVehicle_name(),dto.getVehicle_type(),dto.getCustomer_id(),dto.getCompany_id(),dto.getReceive_date(),dto.getReturn_date(),dto.getStatus(),dto.getVehicle_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Vehicle WHERE vehicle_id=?", id);
    }

    @Override
    public Vehicle search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Vehicle WHERE vehicle_id=?", id + "");
        rst.next();
        return new Vehicle(id + "", rst.getString("vehicle_name"), rst.getString("vehicle_type"),rst.getString("customer_id"),rst.getString("company_id"),rst.getString("receive_date"),rst.getString("return_date"),rst.getString("status"));
    }
}
