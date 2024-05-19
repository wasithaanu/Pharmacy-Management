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
import javafx.scene.layout.AnchorPane;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Salary;
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.model.tm.SalaryTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;
import lk.ijse.Pharmacy.repository.SalaryRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalaryId;
    private String nextId;

    private List<Salary> salaryList = new ArrayList<>();

    public void initialize() {
        this.salaryList = getAllSalary();
        setCellValueFactory();
        loadCustomerTable();
        loadNextOrderId();
    }

    private List<Salary> getAllSalary() {
        List<Salary> salaryList = null;
        try {
            salaryList = SalaryRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salaryList;
    }

     private void loadNextOrderId() {
        try {
            String currentId = SalaryRepo.currentId();
            nextId = nextId(currentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);    //2
            return "S00" + ++id;

        }
        return "O1";
    }
    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

        private void loadCustomerTable() {
            ObservableList<SalaryTm> tmList = FXCollections.observableArrayList();

            for (Salary salary : salaryList) {
                SalaryTm salaryTm = new SalaryTm(
                        salary.getSaId(),
                        salary.getEmId(),
                        salary.getName(),
                        salary.getMonth(),
                        salary.getDate(),
                        salary.getAmount()
                );

                tmList.add(salaryTm);
            }
            tblSalary.setItems(tmList);
            SalaryTm selectedItem =  tblSalary.getSelectionModel().getSelectedItem();
            System.out.println("selectedItem = " + selectedItem);
        }

    @FXML
    void btnClearOnAction(ActionEvent event) { clearFields();}
        private void clearFields() {
            txtSalaryId.setText("");
            txtId.setText("");
            txtName.setText("");
            txtMonth.setText("");
            txtDate.setText("");
            txtAmount.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSalaryId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String saId = nextId;
        String emId = txtId.getText();
        String name = txtName.getText();
        String month = txtMonth.getText();
        String date = txtDate.getText();
        String amount = txtAmount.getText();


        Salary salary = new Salary(saId, emId, name, month, date, amount);

        try {
            boolean isSaved = SalaryRepo.save(salary);
            if (isSaved) new Alert(Alert.AlertType.CONFIRMATION, "salary saved!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String saId = txtSalaryId.getText();
        String emId = txtId.getText();
        String name = txtName.getText();
        String month = txtMonth.getText();
        String date = txtDate.getText();
        String amount = txtAmount.getText();


        Salary salary = new Salary(saId, emId, name, month, date, amount);

        try {
            boolean isUpdated = SalaryRepo.update(salary);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String saId = txtSalaryId.getText();

        try {
            Salary salary = SalaryRepo.searchById(saId);

            if (salary != null) {
                txtSalaryId.setText(salary.getSaId());
                txtId.setText(salary.getEmId());
                txtName.setText(salary.getName());
                txtMonth.setText(salary.getMonth());
                txtDate.setText(salary.getDate());
                txtAmount.setText(salary.getAmount());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}

