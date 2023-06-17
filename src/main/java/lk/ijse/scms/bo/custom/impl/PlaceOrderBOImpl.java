package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.PlaceOrderBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.ItemDAO;
import lk.ijse.scms.dao.custom.OrderDAO;
import lk.ijse.scms.dao.custom.OrderDetailsDAO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.CartDTO;
import lk.ijse.scms.dto.OrderDTO;
import lk.ijse.scms.entity.Item;
import lk.ijse.scms.entity.OrderDetails;
import lk.ijse.scms.entity.Orders;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    @Override
    public boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        DBConnection.getInstance().getConnection().setAutoCommit(false);
        try{
            if (orderDAO.add(new Orders(orderDTO.getOrderId(), LocalDate.now().toString(), orderDTO.getCusId()))) {
                if (saveOrderDetails(orderDTO.getOrderId(), orderDTO.getCartDTOS())){
                    if (updateItemQTY(orderDTO.getCartDTOS())){
                        DBConnection.getInstance().getConnection().commit();
                        return true;
                    }
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        }finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    private boolean updateItemQTY(List<CartDTO> cartDTOS) throws SQLException {
        for (CartDTO cartDTO :cartDTOS) {
            Item item = new Item();
            item.setItemCode(cartDTO.getItemCode());
            item.setQtyOnStock(cartDTO.getQty());
            if (!itemDAO.updateQTY(item)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String oId, List<CartDTO> cartDTOList) throws SQLException, ClassNotFoundException {
        for (CartDTO cartDTO : cartDTOList) {
            OrderDetails orderDetails = new OrderDetails(oId, cartDTO.getItemCode(), cartDTO.getUnitPrice(), cartDTO.getQty(), cartDTO.getUnitPrice()*cartDTO.getQty());
            if (!orderDetailsDAO.add(orderDetails)){
                return false;
            }
        }
        return true;
    }
}
