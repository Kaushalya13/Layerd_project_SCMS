package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.VehicleBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.UserDAO;
import lk.ijse.scms.dao.custom.VehicleDAO;
import lk.ijse.scms.dto.UserDTO;
import lk.ijse.scms.dto.VehicleDTO;
import lk.ijse.scms.entity.User;
import lk.ijse.scms.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public ArrayList<VehicleDTO> getAllVehicle() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> allVehicle= new ArrayList<>();
        ArrayList<Vehicle> all = vehicleDAO.getAll();
        for (Vehicle vehicle : all) {
            allVehicle.add(new VehicleDTO(vehicle.getVehicle_id(),vehicle.getVehicle_name(),vehicle.getVehicle_type(),vehicle.getCustomer_id(),vehicle.getCompany_id(),vehicle.getReceive_date(),vehicle.getReturn_date(),vehicle.getStatus()));
        }
        return allVehicle;
    }

    @Override
    public boolean addVehicle(VehicleDTO dto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.add(new Vehicle(dto.getVehicle_id(),dto.getVehicle_name(),dto.getVehicle_type(),dto.getCustomer_id(),dto.getCompany_id(),dto.getReceive_date(),dto.getReturn_date(),dto.getStatus()));
    }

    @Override
    public boolean updateVehicle(VehicleDTO dto) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(dto.getVehicle_id(),dto.getVehicle_name(),dto.getVehicle_type(),dto.getCustomer_id(),dto.getCompany_id(),dto.getReceive_date(),dto.getReturn_date(),dto.getStatus()));
    }

    @Override
    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(id);
    }

    @Override
    public VehicleDTO searchVehicle(String id) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.search(id);
        return new VehicleDTO(vehicle.getVehicle_id(),vehicle.getVehicle_name(),vehicle.getVehicle_type(),vehicle.getCustomer_id(),vehicle.getCompany_id(),vehicle.getReceive_date(),vehicle.getReturn_date(),vehicle.getStatus());
    }
}
