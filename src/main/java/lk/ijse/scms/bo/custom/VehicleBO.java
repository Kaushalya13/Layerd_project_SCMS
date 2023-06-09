package lk.ijse.scms.bo.custom;

import lk.ijse.scms.bo.SuperBO;
import lk.ijse.scms.dto.UserDTO;
import lk.ijse.scms.dto.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {

    public ArrayList<VehicleDTO> getAllVehicle() throws SQLException, ClassNotFoundException;

    public boolean addVehicle(VehicleDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateVehicle(VehicleDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException;

    public VehicleDTO searchVehicle(String id) throws SQLException, ClassNotFoundException;
}
