package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.OrderDAO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.entity.Orders;
import lk.ijse.scms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<String> loadItemCode() throws SQLException {
        ArrayList<String> allItem = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT itemCode FROM Item");
        while (rst.next()){
            String id = new String(rst.getString("itemCode"));
            allItem.add(id);
        }
        return allItem;
    }

    @Override
    public ArrayList<String> loadCustomerId() throws SQLException {
        ArrayList<String> allCustomer = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT customer_id FROM Customer");
        while (rst.next()){
            String id = new String(rst.getString("customer_id"));
            allCustomer.add(id);
        }
        return allCustomer;
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Orders dto) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Orders(order_id, date, customer_id) VALUES (?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, dto.getOrder_id());
        pstm.setString(2, String.valueOf(LocalDate.now()));
        pstm.setString(3, dto.getCustomer_id());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
