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
import lk.ijse.scms.bo.custom.UserBO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.UserDTO;
import lk.ijse.scms.dto.tm.UserTM;
import lk.ijse.scms.model.UserModel;
import lk.ijse.scms.util.Regex;
import lk.ijse.scms.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306//scms";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtContact_no;

    @FXML
    private ComboBox<String> cmbRanks;

    @FXML
    private JFXTextField txtSerach;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TableColumn<?, ?> colUser_id;

    @FXML
    private TableColumn<?, ?> colUser_name;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colRanks;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colContact_no;

    public AnchorPane loadFormContext;

    UserBO userBO  = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Admi","Cashier");

        cmbRanks.setItems(list);

        getAll();
        setCellValueFactory();
        clearAll();
    }

    void clearAll() {
        txtId.setText(null);
        txtName.setText(null);
        txtPassword.setText(null);
        cmbRanks.setValue(null);
        txtEmail.setText(null);
        txtNic.setText(null);
        txtContact_no.setText(null);
    }

    void setCellValueFactory() {
        colUser_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colUser_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRanks.setCellValueFactory(new PropertyValueFactory<>("ranks"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact_no.setCellValueFactory(new PropertyValueFactory<>("contactno"));
    }

    void getAll() {
        try {
            ArrayList<UserDTO> userDTOList = userBO.getAllUser();

            for (UserDTO userDTO : userDTOList){
                tblUser.getItems().add(new UserTM(userDTO.getUser_id(),userDTO.getUser_name(),userDTO.getPassword(),userDTO.getRanks(),userDTO.getEmail(),userDTO.getNic(),userDTO.getContactno()));
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
        UserDTO userDTO = new UserDTO(txtId.getText(), txtName.getText(), txtPassword.getText(), (String) cmbRanks.getValue(), txtEmail.getText(), txtNic.getText(), txtContact_no.getText());


        if (userBO.addUser(new UserDTO(userDTO.getUser_id(),userDTO.getUser_name(),userDTO.getPassword(),userDTO.getRanks(),userDTO.getEmail(),userDTO.getNic(),userDTO.getContactno()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try agin", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        UserDTO userDTO = new UserDTO(txtId.getText(), txtName.getText(), txtPassword.getText(), (String) cmbRanks.getValue(), txtEmail.getText(), txtNic.getText(), txtContact_no.getText());

        if (userBO.updateUser(new UserDTO(userDTO.getUser_id(),userDTO.getUser_name(),userDTO.getPassword(),userDTO.getRanks(),userDTO.getEmail(),userDTO.getNic(),userDTO.getContactno()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try agin", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (userBO.deleteUser(txtId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try agin", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        userBO.searchUser(txtId.getText());
    }

    public void txtUserIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtId);
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);
    }
    
    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.LANKAN_ID,txtNic);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    public void txtContactNoOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtContact_no);
    }

    public boolean isValidated(){
        if(!Regex.setTextColor(TextFields.INVOICE,txtId))return false;
        if(!Regex.setTextColor(TextFields.NAME,txtName))return false;
        if(!Regex.setTextColor(TextFields.LANKAN_ID,txtNic))return false;
        //if(!Regex.setTextColor(TextFields.PASSWORD,txtPassword))return false;
        if(!Regex.setTextColor(TextFields.EMAIL,txtEmail))return false;
        if(!Regex.setTextColor(TextFields.PHONE,txtContact_no))return false;
        return true;
    }
}
