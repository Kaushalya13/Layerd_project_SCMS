package lk.ijse.scms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.scms.bo.BOFactory;
import lk.ijse.scms.bo.custom.ItemBO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.ItemDTO;
import lk.ijse.scms.dto.tm.ItemTM;
import lk.ijse.scms.model.ItemModel;
import lk.ijse.scms.util.Regex;
import lk.ijse.scms.util.TextFields;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306//scms";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private JFXTextField txtItem_code;

    @FXML
    private JFXTextField txtQtyOnStock;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private ComboBox<String> cmbItem_type;

    @FXML
    private JFXTextField txtSerach;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TableColumn<?, ?> colItem_code;

    @FXML
    private TableColumn<?, ?> colItem_type;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnStock;

    public AnchorPane loadFormContext;

    @FXML
    private TextField txtItem_place;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Oil","Spare parts");
        cmbItem_type.setItems(list);

        getAll();
        setCellValueFactory();
        clearAll();
    }
    void clearAll() {
        txtItem_code.setText(null);
        cmbItem_type.setValue(null);
        txtUnitPrice.setText(null);
        txtQtyOnStock.setText(null);
    }
    void setCellValueFactory() {
        colItem_code.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItem_type.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnStock.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));
    }

    void getAll() {
        tblItem.getItems().clear();
        try {

            List<ItemDTO> itemDTOListDTOList = itemBO.getAllItem();

            for (ItemDTO itemDTO : itemDTOListDTOList){
                tblItem.getItems().add(new ItemTM(itemDTO.getItemCode(),itemDTO.getItemType(),
                        itemDTO.getUnitPrice(),itemDTO.getQtyOnStock()));
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        ItemDTO itemDTO = new ItemDTO(txtItem_code.getText(),(String) cmbItem_type.getValue(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnStock.getText()));


        if (itemBO.addItem(itemDTO)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
       ItemDTO itemDTO = new ItemDTO(txtItem_code.getText(),(String) cmbItem_type.getValue(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnStock.getText()));

        if (itemBO.updateItem(new ItemDTO(itemDTO.getItemCode(),itemDTO.getItemType(),
                itemDTO.getUnitPrice(),itemDTO.getQtyOnStock()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (itemBO.deleteItem(txtItem_code.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id =txtSerach.getText();
        try {
            ItemDTO itemDTO = itemBO.searchItem(id);
            if (itemDTO != null){
                fillDate(itemDTO);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        txtSerach.setText("");
    }

    private void fillDate(ItemDTO itemDTO) {
        txtItem_code.setText(itemDTO.getItemCode());
        cmbItem_type.setValue(itemDTO.getItemType());
        txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        txtQtyOnStock.setText(String.valueOf(itemDTO.getQtyOnStock()));

    }

    public void txtItemCodeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtItem_code);
    }

    public void txtUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtUnitPrice);
    }

    public void txtQtyOnStockOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtQtyOnStock);
    }

    public boolean isValidated(){
        if(!Regex.setTextColor(TextFields.INVOICE,txtItem_code))return false;
        if(!Regex.setTextColor(TextFields.DOUBLE,txtUnitPrice))return false;
        if(!Regex.setTextColor(TextFields.INTEGER,txtQtyOnStock))return false;
        return true;
    }

    public void CreateBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasDesign = JRXmlLoader.load("src\\main\\resources\\reports\\Item.jrxml");
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasPrint,false);
    }
}
