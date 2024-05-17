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
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Employee;
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.model.tm.EmployeeTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;
import lk.ijse.Pharmacy.repository.EmployeeRepo;

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

    private List<Employee> employeeList = new ArrayList<>();

    public void initialize() {
        this.employeeList = getAllEmployee();
        setCellValueFactory();
        loadEmployeeTable();
        loadNextOrderId();
    }
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colIntime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOuttime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void loadNextOrderId() {
        try {
            String currentId = EmployeeRepo.currentId();
             nextId = nextId(currentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("E");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);    //2
            return "E" + ++id;

        }
        return "O1";
    }


    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            EmployeeTm employeeTm = new EmployeeTm(
                    employee.getId(),
                    employee.getName(),
                    employee.getInTime(),
                    employee.getOutTime(),
                    employee.getDate()
            );

            tmList.add(employeeTm);
        }
        tblEmployee.setItems(tmList);
        EmployeeTm selectedItem = (EmployeeTm) tblEmployee.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);


    }


    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    public void mouseClickOnAction(MouseEvent mouseEvent) {
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        txtName.setText(selectedItem.getName());
        txtIntime.setText(selectedItem.getInTime());
        txtOuttime.setText(selectedItem.getOutTime());
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
            boolean isDeleted = EmployeeRepo.delete(name);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = nextId;
        String name = txtName.getText();
        String inTime = txtIntime.getText();
        String outTime = txtOuttime.getText();
        String date = txtDate.getText();

        Employee employee = new Employee(id, name, inTime, outTime, date);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
        } catch (SQLException e) {
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

        Employee employee = new Employee(id, name, inTime, outTime, date);

        try {
            boolean isUpdated =EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            Employee employee = EmployeeRepo.searchById(id);

            if (employee != null) {
                txtId.setText(employee.getId());
                txtName.setText(employee.getName());
                txtIntime.setText(employee.getInTime());
                txtOuttime.setText(employee.getOutTime());
                txtDate.setText(employee.getDate());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
