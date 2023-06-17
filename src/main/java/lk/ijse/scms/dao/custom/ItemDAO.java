package lk.ijse.scms.dao.custom;

import lk.ijse.scms.dao.CrudDAO;
import lk.ijse.scms.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {
    public boolean updateQTY(Item item) throws SQLException;
}
