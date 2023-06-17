package lk.ijse.scms.bo.custom;

import lk.ijse.scms.bo.SuperBO;
import lk.ijse.scms.dto.OrderDTO;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
}
