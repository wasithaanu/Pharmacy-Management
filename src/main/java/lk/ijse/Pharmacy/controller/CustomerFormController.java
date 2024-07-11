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
import lk.ijse.Pharmacy.bo.custom.CustomerBO;
import lk.ijse.Pharmacy.dto.CustomerDTO;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.tm.CustomerTm;


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


    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);



    public void initialize() {

        setCellValueFactory();
        loadCustomerTable();
        loadNextOrderId();
    }

    private void loadNextOrderId() {
        try {
            nextId = customerBO.generateNewCustomerID();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("cu_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cu_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cu_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("cu_contact"));

    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();


        try {
            for (CustomerDTO c : customerBO.getAllCustomers()) {
                CustomerTm customerTm = new CustomerTm(
                        c.getCu_id(),
                        c.getCu_name(),
                        c.getCu_address(),
                        c.getCu_contact()
                );
                tmList.add(customerTm);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblCustomer.setItems(tmList);
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
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
            boolean isDeleted = customerBO.deleteCustomer(name);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                setCellValueFactory();
                loadCustomerTable();
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
        String address = txtAddress.getText();
        Integer contact = Integer.valueOf(txtContact.getText());

        CustomerDTO customer = new CustomerDTO(id, name, address, contact);

        try {
            boolean isSaved = customerBO.addCustomer(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                setCellValueFactory();
                loadCustomerTable();
                loadNextOrderId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = nextId;
        String name = txtName.getText();
        String address = txtAddress.getText();
        Integer contact = Integer.valueOf(txtContact.getText());
        CustomerDTO customer = new CustomerDTO(id, name, address, contact);

        try {
            boolean isUpdated = customerBO.updateCustomer(customer);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                setCellValueFactory();
                loadCustomerTable();
                loadNextOrderId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void mouseClickOnAction(MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        nextId=selectedItem.getCu_id();
        txtName.setText(selectedItem.getCu_name());
        txtAddress.setText(selectedItem.getCu_address());
        txtContact.setText(String.valueOf(selectedItem.getCu_contact()));
    }
    }

