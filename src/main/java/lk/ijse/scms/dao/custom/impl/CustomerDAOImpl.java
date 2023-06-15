package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.CustomerDAO;
import lk.ijse.scms.entity.Customer;
import lk.ijse.scms.entity.Vehicle;
import lk.ijse.scms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");
        while (rst.next()){
            Customer customer = new Customer(rst.getString("customer_id"), rst.getString("customer_name"), rst.getString("nic"),
                    rst.getString("address"), rst.getString("email"), rst.getString("contact_no"));
            allCustomer.add(customer);
        }
        return allCustomer;
    }

    @Override
    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Customer (customer_id,customer_name,nic,address,email,contact_no) VALUES (?,?,?,?,?,?)",
                dto.getCustId(),dto.getCustName(),dto.getNic(),dto.getAddress(),dto.getEmail(),dto.getContactno());
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Customer SET customer_name=?,nic=?,address=?,email=?,contact_no=? WHERE customer_id=?",
                dto.getCustName(),dto.getNic(),dto.getAddress(),dto.getEmail(),dto.getContactno(),dto.getCustId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Customer WHERE customer_id =?",id);
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE customer_id=?", id + "");
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
