package lk.ijse.scms.bo.custom;

import lk.ijse.scms.bo.SuperBO;
import lk.ijse.scms.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException;

    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException;
}
