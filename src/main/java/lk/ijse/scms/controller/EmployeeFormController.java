package lk.ijse.scms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scms.bo.BOFactory;
import lk.ijse.scms.bo.custom.EmployeeBO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.EmployeeDTO;
import lk.ijse.scms.dto.tm.EmployeeTM;
import lk.ijse.scms.entity.Employee;
import lk.ijse.scms.model.EmployeeModel;
import lk.ijse.scms.util.Regex;
import lk.ijse.scms.util.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
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
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtContact_no;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private ComboBox<String> cmbRanks;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TableColumn<?, ?> colEmployee_id;

    @FXML
    private TableColumn<?, ?> colEmployee_name;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colRanks;

    @FXML
    private TableColumn<?, ?> colContact_no;

    public AnchorPane loadFormContext;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Manager","Repairer","Cashier","Mechanic supporters");
        cmbRanks.setItems(list);

        getAll();
        setCellValueFactory();
        clearAll();
    }

    void clearAll() {
        txtId.setText(null);
        txtName.setText(null);
        txtNic.setText(null);
        txtAddress.setText(null);
        cmbRanks.setValue(null);
        txtContact_no.setText(null);
    }
    void setCellValueFactory() {
        colEmployee_id.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colEmployee_name.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRanks.setCellValueFactory(new PropertyValueFactory<>("ranks"));
        colContact_no.setCellValueFactory(new PropertyValueFactory<>("contactno"));
    }

    void getAll() {
        tblEmployee.getItems().clear();
        try {
            ArrayList<EmployeeDTO> employeeDTOList = employeeBO.getAllEmployee();

            for (EmployeeDTO employeeDTO : employeeDTOList){
                tblEmployee.getItems().add(new EmployeeTM(employeeDTO.getEmployee_id(),employeeDTO.getEmployee_name(),
                        employeeDTO.getNic(),employeeDTO.getAddress(),employeeDTO.getRanks(),employeeDTO.getContactno()));
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
        EmployeeDTO employeeDTO = new EmployeeDTO(txtId.getText(),txtName.getText(),txtNic.getText(),txtAddress.getText(),(String) cmbRanks.getValue(), txtContact_no.getText());

        if (employeeBO.addEmployee(employeeDTO)){
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
        EmployeeDTO employeeDTO = new EmployeeDTO(txtId.getText(),txtName.getText(),txtNic.getText(),txtAddress.getText(),(String) cmbRanks.getValue(), txtContact_no.getText());

        if (employeeBO.updateEmployee(new EmployeeDTO(employeeDTO.getEmployee_id(),employeeDTO.getEmployee_name(),employeeDTO.getNic(),
                employeeDTO.getAddress(),employeeDTO.getRanks(),employeeDTO.getContactno()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (employeeBO.deleteEmployee(txtId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id =txtSearch.getText();
        try {
            EmployeeDTO employeeDTO = employeeBO.searchEmployee(id);
            if (employeeDTO !=null){
                fillDate(employeeDTO);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void fillDate(EmployeeDTO employeeDTO) {
        txtId.setText(employeeDTO.getEmployee_id());
        txtName.setText(employeeDTO.getEmployee_name());
        txtNic.setText(employeeDTO.getNic());
        txtAddress.setText(employeeDTO.getAddress());
        cmbRanks.setValue(employeeDTO.getRanks());
        txtContact_no.setText(employeeDTO.getContactno());
    }

    public void txtEmployeeIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtId);
    }

    public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.NAME,txtName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);
    }

    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.LANKAN_ID,txtNic);
    }

    public void txtContactNoOnKeyReleased(KeyEvent keyEvent) {

        Regex.setTextColor(TextFields.PHONE,txtContact_no);
    }

    public boolean isValidated(){
        if(!Regex.setTextColor(TextFields.INVOICE,txtId))return false;
        if(!Regex.setTextColor(TextFields.NAME,txtName))return false;
        if(!Regex.setTextColor(TextFields.ADDRESS,txtAddress))return false;
        if(!Regex.setTextColor(TextFields.LANKAN_ID,txtNic))return false;
        if(!Regex.setTextColor(TextFields.PHONE,txtContact_no))return false;
        return true;
    }


}
