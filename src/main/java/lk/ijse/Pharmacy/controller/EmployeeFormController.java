package lk.ijse.Pharmacy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Pharmacy.bo.BOFactory;
import lk.ijse.Pharmacy.bo.custom.EmployeeBO;
import lk.ijse.Pharmacy.dto.CustomerDTO;
import lk.ijse.Pharmacy.dto.EmployeeDTO;
import lk.ijse.Pharmacy.model.Employee;
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.model.tm.EmployeeTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colIntime;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colOuttime;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;
    private String nextId;

    @FXML
    private TextField txtIntime;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOuttime;



    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPlOYEE);

    public void initialize() {
        setCellValueFactory();
        loadEmployeeTable();
        loadNextOrderId();
    }
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colIntime.setCellValueFactory(new PropertyValueFactory<>("in_time"));
        colOuttime.setCellValueFactory(new PropertyValueFactory<>("out_time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadNextOrderId() {
        try {
            nextId = employeeBO.generateNewEmployeeID();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        try {
            for (EmployeeDTO c : employeeBO.getAllEmployee()) {
                EmployeeTm employeeTm = new EmployeeTm(
                        c.getE_id(),
                        c.getE_name(),
                        c.getIn_time(),
                        c.getOut_time(),
                        c.getDate()
                );
                tmList.add(employeeTm);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblEmployee.setItems(tmList);
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);

    }



    public void mouseClickOnAction(MouseEvent mouseEvent) {
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        txtName.setText(selectedItem.getE_name());
        txtIntime.setText(selectedItem.getIn_time());
        txtOuttime.setText(selectedItem.getOut_time());
        txtDate.setText(selectedItem.getDate());
       // txtContact.setText(String.valueOf(selectedItem.getContact()));
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtIntime.setText("");
        txtOuttime.setText("");
        txtDate.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String name = txtName.getText();
        try {
            boolean isDeleted = employeeBO.deleteEmployee(name);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                setCellValueFactory();
                loadEmployeeTable();
                loadNextOrderId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = nextId;
        String name = txtName.getText();
        String inTime = txtIntime.getText();
        String outTime = txtOuttime.getText();
        String date = txtDate.getText();

        EmployeeDTO employee = new EmployeeDTO(id, name, inTime, outTime, date);



        try {
            boolean isSaved = employeeBO.addEmployee(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                setCellValueFactory();
                loadEmployeeTable();
                loadNextOrderId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String inTime = txtIntime.getText();
        String outTime = txtOuttime.getText();
        String date = txtDate.getText();

        EmployeeDTO employee = new EmployeeDTO(id, name, inTime, outTime, date);

        try {
            boolean isUpdated = employeeBO.updateEmployee(employee);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                setCellValueFactory();
                loadEmployeeTable();
                loadNextOrderId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
//        String id = txtId.getText();
//
//        try {
//            Employee employee = EmployeeRepo.searchById(id);
//
//            if (employee != null) {
//                txtId.setText(employee.getId());
//                txtName.setText(employee.getName());
//                txtIntime.setText(employee.getInTime());
//                txtOuttime.setText(employee.getOutTime());
//                txtDate.setText(employee.getDate());
//
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
    }

}
