package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.OrderDetailsDAO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.entity.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetails dto) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        double total = dto.getQuantity() * dto.getUnitPrice();
        String sql = "INSERT INTO orderDetails(order_id, itemCode, unitPrice, quantity,total) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, dto.getOrder_id());
        pstm.setString(2, dto.getItemCode());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQuantity());
        pstm.setDouble(5, total);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
