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
import lk.ijse.scms.bo.custom.VehicleBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.CompanyDAO;
import lk.ijse.scms.dao.custom.CustomerDAO;
import lk.ijse.scms.dao.custom.VehicleDAO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.CompanyDTO;
import lk.ijse.scms.dto.CustomerDTO;
import lk.ijse.scms.dto.VehicleDTO;
import lk.ijse.scms.dto.tm.VehicleTM;
import lk.ijse.scms.entity.Vehicle;
import lk.ijse.scms.model.CompanyModel;
import lk.ijse.scms.model.CustomerModel;
import lk.ijse.scms.model.VehicleModel;
import lk.ijse.scms.util.Regex;
import lk.ijse.scms.util.TextFields;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;


public class VehicleFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306//scms";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private JFXTextField txtId;

    @FXML
    private ComboBox<String> cmbVehicle_name;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private ComboBox<String> cmbCompany_id;

    @FXML
    private ComboBox<String> cmbCustomer_id;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private DatePicker Date;

    @FXML
    private DatePicker ReturnDate;

    @FXML
    private TableView<VehicleTM> tblVehicle;

    @FXML
    private TableColumn<?, ?> colCustomer_id;

    @FXML
    private TableColumn<?, ?> colVehicle_id;

    @FXML
    private TableColumn<?, ?> colVehicle_name;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colReceive_date;

    @FXML
    private TableColumn<?, ?> colReturn_date;

    @FXML
    private TableColumn<?, ?> colCompany_id;

    @FXML
    private TableColumn<?, ?> colStatus;

    public AnchorPane loadFormContext;

    private String customer_id;
    private String company_id;

    public static ArrayList<CustomerDTO> customerDTOArrayList= new ArrayList();
    public static ArrayList<CompanyDTO> companyDTOArrayList = new ArrayList();

    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.VEHICLE);

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.VEHICLE);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Honda","Suzuki","Yamaha Ray ZR","TVS","BAJAJ","KTM","Hero","CT 100","Dio","Discover","Hornet","Other");
        cmbVehicle_name.setItems(list);

        ObservableList<String> list1 = FXCollections.observableArrayList("Moped","Scooter","Cruiser","Trial Bikes","Dual Purpose","Sport Bikes","Other");
        cmbType.setItems(list1);

        getAll();
        setCellValueFactory();
        clearAll();

        try {
            loadComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            loadCompanyId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void loadCompanyId() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        List<String> companyId= vehicleDAO.loadCompanyId();
        for (String c : companyId){
            list.add(c);
        }
        cmbCompany_id.setItems(list);
    }

    void loadComboBox() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        List<String> customerId = vehicleDAO.loadComboBox();
        for (String a : customerId) {
            list.add(a);
        }
        cmbCustomer_id.setItems(list);
    }

    void clearAll() {
        cmbCustomer_id.setValue(null);
        txtId.setText(null);
        cmbVehicle_name.setValue(null);
        cmbType.setValue(null);
        Date.setValue(null);
        ReturnDate.setValue(null);
        cmbCompany_id.setValue(null);
    }

    void setCellValueFactory() {
        colCustomer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colVehicle_id.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
        colVehicle_name.setCellValueFactory(new PropertyValueFactory<>("vehicle_name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicle_type"));
        colReceive_date.setCellValueFactory(new PropertyValueFactory<>("receive_date"));
        colReturn_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        colCompany_id.setCellValueFactory(new PropertyValueFactory<>("company_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    void getAll() {
        tblVehicle.getItems().clear();
        try {
            ArrayList<VehicleDTO> vehicleDTOList = vehicleBO.getAllVehicle();

            for (VehicleDTO vehicleDTO : vehicleDTOList){
               tblVehicle.getItems().add(new VehicleTM(vehicleDTO.getVehicle_id(),vehicleDTO.getVehicle_name(),
                       vehicleDTO.getVehicle_type(),vehicleDTO.getCustomer_id(),vehicleDTO.getCompany_id(),vehicleDTO.getReceive_date(),vehicleDTO.getReturn_date(),vehicleDTO.getStatus()));
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
        String date=Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String returndate=ReturnDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        VehicleDTO vehicleDTO = new VehicleDTO(txtId.getText(), (String) cmbVehicle_name.getValue(),(String) cmbType.getValue(),(String) cmbCustomer_id.getValue(),(String) cmbCompany_id.getValue(),date,returndate,"Active");

        if(vehicleBO.addVehicle(vehicleDTO)){
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
        String date=Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String returndate=ReturnDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        VehicleDTO vehicleDTO = new VehicleDTO(txtId.getText(), (String) cmbVehicle_name.getValue(),(String) cmbType.getValue(),
                (String) cmbCustomer_id.getValue(),(String) cmbCompany_id.getValue(),date,returndate,"Active");

        if (vehicleBO.updateVehicle(new VehicleDTO(vehicleDTO.getVehicle_id(),vehicleDTO.getVehicle_name(),vehicleDTO.getVehicle_type(),vehicleDTO.getCustomer_id(),
                vehicleDTO.getCompany_id(),vehicleDTO.getReceive_date(),vehicleDTO.getReturn_date(),vehicleDTO.getStatus()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (vehicleBO.deleteVehicle(txtId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtSearch.getText();
        try {
            VehicleDTO vehicleDTO = vehicleBO.searchVehicle(id);
            if (vehicleDTO != null){
                fillDate(vehicleDTO);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtSearch.setText("");
    }

    private void fillDate(VehicleDTO vehicleDTO) {
        cmbCustomer_id.setValue(vehicleDTO.getCustomer_id());
        txtId.setText(vehicleDTO.getVehicle_id());
        cmbVehicle_name.setValue(vehicleDTO.getVehicle_name());
        cmbType.setValue(vehicleDTO.getVehicle_type());
        Date.setValue(LocalDate.parse(vehicleDTO.getReceive_date()));
        ReturnDate.setValue(LocalDate.parse(vehicleDTO.getReturn_date()));
        cmbCompany_id.setValue(vehicleDTO.getCompany_id());
    }

    public void cmbCustomer_idOnAction(ActionEvent actionEvent) {
        customer_id = (String) cmbCustomer_id.getValue();
        try {
            CustomerDTO customerDTO = CustomerModel.search(customer_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void cmbCompany_idOnAction(ActionEvent actionEvent) {
        company_id = (String) cmbCompany_id.getValue();
        try {
            CompanyDTO companyDTO = CompanyModel.search(company_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnReturnVehicleOnAction(ActionEvent actionEvent) {
        Integer index = tblVehicle.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            new Alert(Alert.AlertType.WARNING , "Please CLick Row !!").show();
            return;
        }
        String id = colVehicle_id.getCellData(index).toString();
        setReturned(id);

    }

    private void setReturned(String id) {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Vehicle SET status = ? WHERE vehicle_id = ?");

                pstm.setString(1,"Returned");
                pstm.setString(2,id);

                int add = pstm.executeUpdate();

                if (add > 0){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try agin", ButtonType.OK).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getAll();
    }

    public void txtVehicleIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INVOICE,txtId);
    }

    public boolean isValidated(){
        if(!Regex.setTextColor(TextFields.INVOICE,txtId))return false;
        return true;
    }
}
