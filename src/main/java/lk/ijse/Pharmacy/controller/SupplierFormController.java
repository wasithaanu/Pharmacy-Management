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
import lk.ijse.Pharmacy.model.Supplier;
import lk.ijse.Pharmacy.model.tm.SupplierTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;
import lk.ijse.Pharmacy.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colAdress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private List<Supplier> supplierList = new ArrayList<>();

    public void initialize() {
        this.supplierList = getAllSuppliers();
        setCellValueFactory();
        loadSupplierTable();
    }

    private void loadSupplierTable() {
        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

        for (Supplier supplier : supplierList) {
            SupplierTm supplierTm = new SupplierTm(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getContact()
            );

            tmList.add(supplierTm);
        }
        tblSupplier.setItems(tmList);
        SupplierTm selectedItem = (SupplierTm) tblSupplier.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private List<Supplier> getAllSuppliers() {
        List<Supplier> supplierList = null;
        try {
            supplierList = SupplierRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return supplierList;

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Integer contact = Integer.valueOf(txtContact.getText());

        Supplier supplier = new Supplier(id, name, address, contact);

        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved) new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Integer contact = Integer.valueOf(txtContact.getText());

        Supplier supplier = new Supplier(id, name, address, contact);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            Supplier supplier = SupplierRepo.searchById(id);

            if (supplier != null) {
                txtId.setText(supplier.getId());
                txtName.setText(supplier.getName());
                txtAddress.setText(supplier.getAddress());
                txtContact.setText(String.valueOf(supplier.getContact()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
