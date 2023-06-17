package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.ItemDAO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.entity.Item;
import lk.ijse.scms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItem = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
        while (rst.next()){
            Item item = new Item(rst.getString("itemCode"), rst.getString("itemType"),
                    rst.getDouble("unitPrice"), rst.getInt("qtyOnStock"));
            allItem.add(item);
        }
        return allItem;
    }

    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Item (itemCode,itemType,unitPrice,qtyOnStock) VALUES (?,?,?,?)",
                dto.getItemCode(),dto.getItemType(),dto.getUnitPrice(),dto.getQtyOnStock());
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET itemType =?,unitPrice=?,qtyOnStock=? WHERE itemCode =?",
                dto.getItemType(),dto.getUnitPrice(),dto.getQtyOnStock(),dto.getItemCode());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE itemCode =?", id);
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE itemCode=?", id + "");
        rst.next();
        return new Item(id +"",rst.getString("itemType"),rst.getDouble("unitPrice"),rst.getInt("qtyOnStock"));

    }

    @Override
    public boolean updateQTY(Item item) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Item SET qtyOnStock = (qtyOnStock - ?) WHERE itemCode = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, item.getQtyOnStock());
        pstm.setString(2, item.getItemCode());

        return pstm.executeUpdate() > 0;
    }
}
