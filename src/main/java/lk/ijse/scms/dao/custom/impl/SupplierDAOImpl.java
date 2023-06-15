package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.CrudDAO;
import lk.ijse.scms.dao.custom.SupplierDAO;
import lk.ijse.scms.entity.Supplier;
import lk.ijse.scms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> allSupplier = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");
        while (rst.next()){
            Supplier supplier = new Supplier(rst.getString("supplier_id"), rst.getString("supplier_name"),
                    rst.getString("address"), rst.getString("email"), rst.getString("contact_no"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }

    @Override
    public boolean add(Supplier dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Supplier (supplier_id,supplier_name,address,email,contact_no)VALUES (?,?,?,?,?)",
                dto.getSupplier_id(),dto.getSupplier_name(),dto.getAddress(),dto.getEmail(),dto.getContactno());
    }

    @Override
    public boolean update(Supplier dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Supplier SET supplier_name=?,address=?,email=?,contact_no=? WHERE supplier_id=?",
                dto.getSupplier_name(),dto.getAddress(),dto.getEmail(),dto.getContactno(),dto.getSupplier_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE supplier_id=?", id);
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier WHERE supplier_id=?",id +"");
        rst.next();
        return new Supplier(id +"",rst.getString("supplier_name"),rst.getString("address"),
                rst.getString("email"),rst.getString("contact_no"));
    }
}
