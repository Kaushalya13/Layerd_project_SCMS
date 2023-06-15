package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.SupplierBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.SupplierDAO;
import lk.ijse.scms.dto.SupplierDTO;
import lk.ijse.scms.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> allSupplier = new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();
        for (Supplier supplier : all){
            allSupplier.add(new SupplierDTO(supplier.getSupplier_id(),supplier.getSupplier_name(),
                    supplier.getAddress(),supplier.getEmail(),supplier.getContactno()));
        }
        return allSupplier;
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(dto.getSupplier_id(),dto.getSupplier_name(),dto.getAddress(),
                dto.getEmail(),dto.getContactno()));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getSupplier_id(),dto.getSupplier_name(),dto.getAddress(),
                dto.getEmail(),dto.getContactno()));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public SupplierDTO searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(id);
        return new SupplierDTO(supplier.getSupplier_id(),supplier.getSupplier_name(),supplier.getAddress(),
                supplier.getEmail(),supplier.getContactno());
    }
}
