package lk.ijse.scms.dto;

import java.util.List;

public class OrderDTO {
    private String orderId;
    private String cusId;
    private List<CartDTO> cartDTOS;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String cusId, List<CartDTO> cartDTOS) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.cartDTOS = cartDTOS;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public List<CartDTO> getCartDTOS() {
        return cartDTOS;
    }

    public void setCartDTOS(List<CartDTO> cartDTOS) {
        this.cartDTOS = cartDTOS;
    }
}
