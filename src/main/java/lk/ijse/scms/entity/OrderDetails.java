package lk.ijse.scms.entity;

public class OrderDetails {
    private String order_id;
    private String itemCode;
    private Double unitPrice;
    private Integer quantity;
    private Double total;

    public OrderDetails() {
    }

    public OrderDetails(String order_id, String itemCode, Double unitPrice, Integer quantity, Double total) {
        this.order_id = order_id;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "order_id='" + order_id + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
