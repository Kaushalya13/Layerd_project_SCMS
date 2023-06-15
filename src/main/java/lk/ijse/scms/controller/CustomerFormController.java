package lk.ijse.scms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scms.bo.BOFactory;
import lk.ijse.scms.bo.custom.CustomerBO;
import lk.ijse.scms.bo.custom.UserBO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.CustomerDTO;
import lk.ijse.scms.dto.tm.CustomerTM;
import lk.ijse.scms.model.CustomerModel;
import lk.ijse.scms.util.Regex;
import lk.ijse.scms.util.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306//scms";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn<?, ?> colCustmoer_id;

    @FXML
    private TableColumn<?, ?> colCustomer_name;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colContact_no;

    public AnchorPane loadFormContext;

    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
        clearAll();
    }

    void clearAll() {
        txtId.setText(null);
        txtName.setText(null);
        txtNic.setText(null);
        txtAddress.setText(null);
        txtContactNo.setText(null);
    }

    void setCellValueFactory() {
        colCustmoer_id.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCustomer_name.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact_no.setCellValueFactory(new PropertyValueFactory<>("contactno"));
    }

    void getAll(){
        tblCustomer.getItems().clear();
        try {
            List<CustomerDTO> custDTOList = customerBO.getAllCustomer();

            for (CustomerDTO customerDTO : custDTOList){
               tblCustomer.getItems().add(new CustomerTM(customerDTO.getCustId(),customerDTO.getCustName(),
                       customerDTO.getNic(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getContactno()));
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
        CustomerDTO custDTO = new CustomerDTO(txtId.getText(),txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),txtContactNo.getText());

        if (customerBO.addCustomer(custDTO)){
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
        CustomerDTO custDTO = new CustomerDTO(txtId.getText(),txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),txtContactNo.getText());

        if (customerBO.updateCustomer(new CustomerDTO(custDTO.getCustId(),custDTO.getCustName(),
                custDTO.getNic(),custDTO.getAddress(),custDTO.getEmail(),custDTO.getContactno()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (customerBO.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();
        try {
            CustomerDTO customerDTO = customerBO.searchCustomer(id);
            if (customerDTO != null){
                fillDate(customerDTO);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        txtSearch.setText("");
    }

    private void fillDate(CustomerDTO customerDTO) {
        txtId.setText(customerDTO.getCustId());
        txtName.setText(customerDTO.getCustName());
        txtNic.setText(customerDTO.getNic());
        txtAddress.setText(customerDTO.getAddress());
        txtEmail.setText(customerDTO.getEmail());
        txtContactNo.setText(customerDTO.getContactno());
    }

    public void txtCustomerIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtId);
    }

    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);
    }

    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.LANKAN_ID,txtNic);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    public void txtContactNoOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtContactNo);
    }

    public boolean isValidated(){
        if(!Regex.setTextColor(TextFields.INVOICE,txtId))return false;
        if(!Regex.setTextColor(TextFields.NAME,txtName))return false;
        if(!Regex.setTextColor(TextFields.ADDRESS,txtAddress))return false;
        if(!Regex.setTextColor(TextFields.LANKAN_ID,txtNic))return false;
        if(!Regex.setTextColor(TextFields.PHONE,txtContactNo))return false;
        return true;
    }
}
