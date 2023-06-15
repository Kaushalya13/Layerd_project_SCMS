package lk.ijse.scms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.scms.bo.BOFactory;
import lk.ijse.scms.bo.custom.SupplierBO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.SupplierDTO;
import lk.ijse.scms.dto.tm.SupplierTM;
import lk.ijse.scms.model.SupplierModel;
import lk.ijse.scms.util.Regex;
import lk.ijse.scms.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306//scms";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TableColumn<?, ?> colSupplier_id;

    @FXML
    private TableColumn<?, ?> colSupplier_name;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colContact_no;

    public AnchorPane loadFormContext;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
        clearAll();
    }

    void clearAll() {
        txtId.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtEmail.setText(null);
        txtContactNo.setText(null);
    }

    void setCellValueFactory() {
        colSupplier_id.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colSupplier_name.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact_no.setCellValueFactory(new PropertyValueFactory<>("contactno"));
    }

    void getAll(){
        tblSupplier.getItems().clear();
        try {
            List<SupplierDTO> supplierDTOList = supplierBO.getAllSupplier();

            for (SupplierDTO supplierDTO : supplierDTOList){
               tblSupplier.getItems().add(new SupplierTM(supplierDTO.getSupplier_id(),supplierDTO.getSupplier_name(),
                       supplierDTO.getAddress(),supplierDTO.getEmail(),supplierDTO.getContactno()));
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
        SupplierDTO supplierDTO = new SupplierDTO(txtId.getText(), txtName.getText(), txtAddress.getText(), txtEmail.getText(), txtContactNo.getText());

        if (supplierBO.addSupplier(supplierDTO)){
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
        SupplierDTO supplierDTO = new SupplierDTO(txtId.getText(), txtName.getText(), txtAddress.getText(), txtEmail.getText(), txtContactNo.getText());

        if (supplierBO.updateSupplier(new SupplierDTO(supplierDTO.getSupplier_id(),supplierDTO.getSupplier_name(),
                supplierDTO.getAddress(),supplierDTO.getEmail(),supplierDTO.getContactno()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try agin",ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (supplierBO.deleteSupplier(txtId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try agin",ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();
        try {
            SupplierDTO supplierDTO = supplierBO.searchSupplier(id);
            if (supplierDTO != null){
                fillDate(supplierDTO);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void fillDate(SupplierDTO supplierDTO) {
        txtId.setText(supplierDTO.getSupplier_id());
        txtName.setText(supplierDTO.getSupplier_name());
        txtAddress.setText(supplierDTO.getAddress());
        txtEmail.setText(supplierDTO.getEmail());
        txtContactNo.setText(supplierDTO.getContactno());
    }

    public void txtSupplierIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtId);
    }

    public void txtSupplierNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextFields.EMAIL,txtEmail); }

    public void txtContactNoOnKeyReleased(KeyEvent keyEvent) { Regex.setTextColor(TextFields.PHONE,txtContactNo); }

    public boolean isValidated(){
        if(!Regex.setTextColor(TextFields.INVOICE,txtId))return false;
        if(!Regex.setTextColor(TextFields.NAME,txtName))return false;
        if(!Regex.setTextColor(TextFields.ADDRESS,txtAddress))return false;
        if(!Regex.setTextColor(TextFields.EMAIL,txtEmail))return false;
        if(!Regex.setTextColor(TextFields.PHONE,txtContactNo))return false;
        return true;
    }
}