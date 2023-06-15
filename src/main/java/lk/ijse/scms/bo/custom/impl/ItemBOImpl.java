package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.ItemBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.ItemDAO;
import lk.ijse.scms.dto.ItemDTO;
import lk.ijse.scms.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItem = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all){
            allItem.add(new ItemDTO(item.getItemCode(),item.getItemType(),item.getUnitPrice(),item.getQtyOnStock()));
        }
        return allItem;
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItemCode(),dto.getItemType(),dto.getUnitPrice(),dto.getQtyOnStock()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemCode(),dto.getItemType(),dto.getUnitPrice(),dto.getQtyOnStock()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        return new ItemDTO(item.getItemCode(),item.getItemType(),item.getUnitPrice(),item.getQtyOnStock());
    }
}
