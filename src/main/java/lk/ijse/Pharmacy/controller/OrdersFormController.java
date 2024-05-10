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
import lk.ijse.Pharmacy.model.Order;
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.model.tm.OrderTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;
import lk.ijse.Pharmacy.repository.OrdersRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersFormController {

    @FXML
    private TableColumn<?, ?> colAnimal;

    @FXML
    private TableColumn<?, ?> colCusid;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDisesses;

    @FXML
    private TableColumn<?, ?> colEmpid;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<OrderTm> tblOrders;

    @FXML
    private TextField txtAnimal;

    @FXML
    private TextField txtCusid;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDisesses;

    @FXML
    private TextField txtEmpid;

    @FXML
    private TextField txtId;

    private List<Order> orderList = new ArrayList<>();

    public void initialize() {
        this.orderList = getAllOrders();
        setCellValueFactory();
        loadOrdersTable();
    }

    private void loadOrdersTable() {
        ObservableList<OrderTm> tmList = FXCollections.observableArrayList();

        for (Order order : orderList) {
            OrderTm orderTm = new OrderTm(
                    order.getId(),
                    order.getDate(),
                    order.getAnimal(),
                    order.getDiseases(),
                    order.getCusId(),
                    order.getEmpId()
            );

            tmList.add(orderTm);
        }
        tblOrders.setItems(tmList);
        OrderTm selectedItem = tblOrders.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAnimal.setCellValueFactory(new PropertyValueFactory<>("animal"));
        colDisesses.setCellValueFactory(new PropertyValueFactory<>("diseases"));
        colCusid.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colEmpid.setCellValueFactory(new PropertyValueFactory<>("empId"));
    }

    private List<Order> getAllOrders() {
        List<Order> orderList = null;
        try {
            orderList = OrdersRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtDate.setText("");
        txtAnimal.setText("");
        txtDisesses.setText("");
        txtCusid.setText("");
        txtEmpid.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = OrdersRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "order deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String date = txtDate.getText();
        String animal = txtAnimal.getText();
        String diseases = txtDisesses.getText();
        String cusId = txtCusid.getText();
        String empId = txtEmpid.getText();

        Order order = new Order(id, date, animal, diseases,cusId,empId);

        try {
            boolean isSaved = OrdersRepo.save(order);
            if (isSaved) new Alert(Alert.AlertType.CONFIRMATION, "order saved!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String date = txtDate.getText();
        String animal = txtAnimal.getText();
        String diseases = txtDisesses.getText();
        String cusId = txtCusid.getText();
        String empId = txtEmpid.getText();

        Order order = new Order(id, date, animal, diseases,cusId,empId);

        try {
            boolean isUpdated = OrdersRepo.update(order);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "order updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            Order order = OrdersRepo.searchById(id);

            if (order != null) {
                txtId.setText(order.getId());
                txtDate.setText(order.getDate());
                txtAnimal.setText(order.getAnimal());
                txtDisesses.setText(order.getDiseases());
                txtCusid.setText(order.getCusId());
                txtEmpid.setText(order.getEmpId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
