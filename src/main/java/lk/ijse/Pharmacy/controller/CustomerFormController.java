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
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtid;
    private String nextId;

    private List<Customer> customerList = new ArrayList<>();

    public void initialize() {
        this.customerList = getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
        loadNextOrderId();
    }

    private void loadNextOrderId() {
        try {
            String currentId = CustomerRepo.currentId();
            nextId = nextId(currentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);    //2
            return "C00" + ++id;

        }
        return "1";
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            CustomerTm customerTm = new CustomerTm(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact()
            );

            tmList.add(customerTm);
        }
        tblCustomer.setItems(tmList);
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Customer> getAllCustomers() {
        List<Customer> customerList = null;
        try {
            customerList = CustomerRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    @FXML
    void btnClearOnAction(ActionEvent event){
        clear();
    }

    private void clear() {
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String name = txtName.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(name);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = nextId;
        String name = txtName.getText();
        String address = txtAddress.getText();
        Integer contact = Integer.valueOf(txtContact.getText());

        Customer customer = new Customer(id, name, address, contact);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = nextId;
        String name = txtName.getText();
        String address = txtAddress.getText();
        Integer contact = Integer.valueOf(txtContact.getText());
        Customer customer = new Customer(id, name, address, contact);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtid.getText();

        try {
            Customer customer = CustomerRepo.searchById(id);

            if (customer != null) {
                txtid.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtContact.setText(String.valueOf(customer.getContact()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void mouseClickOnAction(MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        nextId=selectedItem.getId();
        txtName.setText(selectedItem.getName());
        txtAddress.setText(selectedItem.getAddress());
        txtContact.setText(String.valueOf(selectedItem.getContact()));
    }
    }

