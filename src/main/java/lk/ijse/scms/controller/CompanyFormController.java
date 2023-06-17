package lk.ijse.scms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.scms.bo.BOFactory;
import lk.ijse.scms.bo.custom.CompanyBO;
import lk.ijse.scms.db.DBConnection;
import lk.ijse.scms.dto.CompanyDTO;
import lk.ijse.scms.dto.tm.CompanyTM;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class CompanyFormController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306//scms";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private JFXTextField txtId;


    @FXML
    private ComboBox<String> cmbCompany_name;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<CompanyTM> tblCompany;

    @FXML
    private TableColumn<?, ?> colCompany_id;

    @FXML
    private TableColumn<?, ?> colCompany_name;

    @FXML
    private TableColumn<?, ?> colType;

    public AnchorPane loadFormContext;

    CompanyBO companyBO = (CompanyBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COMPANY);

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Ranathunga Motors", "Mihiri Motors", "Other Company");
        cmbCompany_name.setItems(list);

        ObservableList<String> list1 = FXCollections.observableArrayList("First Service", "Second Service", "Other Service");
        cmbType.setItems(list1);

        clearAll();
        getAll();
        setCellValueFactory();
    }

    void clearAll() {
        txtId.setText(null);
        cmbCompany_name.setValue(null);
        cmbType.setValue(null);
    }

    void setCellValueFactory() {
        colCompany_id.setCellValueFactory(new PropertyValueFactory<>("company_id"));
        colCompany_name.setCellValueFactory(new PropertyValueFactory<>("company_name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("company_type"));
    }

    void getAll() {
        tblCompany.getItems().clear();
        try {
            List<CompanyDTO> companyDTOList = companyBO.getAllCompany();

            for (CompanyDTO companyDTO : companyDTOList) {
                tblCompany.getItems().add(new CompanyTM(companyDTO.getCompany_id(), companyDTO.getCompany_name(), companyDTO.getCompany_type()));
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CompanyDTO companyDTO = new CompanyDTO(txtId.getText(), (String) cmbCompany_name.getValue(), (String) cmbType.getValue());
        if (companyBO.addCompany(companyDTO)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CompanyDTO companyDTO = new CompanyDTO(txtId.getText(), (String) cmbCompany_name.getValue(), (String) cmbType.getValue());
        if (companyBO.updateCompany(new CompanyDTO(companyDTO.getCompany_id(), companyDTO.getCompany_name(), companyDTO.getCompany_type()))) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (companyBO.deleteCompany(txtId.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
        }

        getAll();
        clearAll();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();
        try {
            CompanyDTO companyDTO = companyBO.searchCompany(id);
            if (companyDTO != null) {
                fillDate(companyDTO);
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        txtSearch.setText("");
    }

    private void fillDate(CompanyDTO companyDTO) {
        txtId.setText(companyDTO.getCompany_id());
        cmbCompany_name.setValue(companyDTO.getCompany_name());
        cmbType.setValue(companyDTO.getCompany_type());
    }
}
