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
import lk.ijse.Pharmacy.model.Stock;
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.model.tm.StockTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;
import lk.ijse.Pharmacy.repository.StockRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockFormController {

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitprice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<StockTm> tblStock;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitprice;

    private List<Stock> stockList = new ArrayList<>();

    public void initialize() {
        this.stockList = getAllStock();
        setCellValueFactory();
        loadStockTable();
    }

    private void loadStockTable() {
        ObservableList<StockTm> tmList = FXCollections.observableArrayList();

        for (Stock stock : stockList) {
            StockTm stockTm = new StockTm(
                    stock.getId(),
                    stock.getName(),
                    stock.getUnitPrice(),
                    stock.getQty()
            );

            tmList.add(stockTm);
        }
        tblStock.setItems(tmList);
        StockTm selectedItem = (StockTm) tblStock.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private List<Stock> getAllStock() {
        List<Stock> stockList = null;
        try {
            stockList = StockRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockList;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCode.setText("");
        txtName.setText("");
        txtUnitprice.setText("");
        txtQty.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCode.getText();

        try {
            boolean isDeleted = StockRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "stock deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCode.getText();
        String name = txtName.getText();
        Double unitPrice = Double.valueOf(txtUnitprice.getText());
        Integer qty = Integer.valueOf(txtQty.getText());

        Stock stock = new Stock(id, name, unitPrice, qty);

        try {
            boolean isSaved = StockRepo.save(stock);
            if (isSaved) new Alert(Alert.AlertType.CONFIRMATION, "stock saved!").show();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            initialize();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCode.getText();
        String name = txtName.getText();
        Double unitPrice = Double.valueOf(txtUnitprice.getText());
        Integer qty = Integer.valueOf(txtQty.getText());

        Stock stock = new Stock(id, name, unitPrice, qty);
        System.out.println(stock);
        try {
            boolean isUpdated = StockRepo.update(stock);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "stock updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtCode.getText();

        try {
            Stock stock = StockRepo.searchById(id);

            if (stock != null) {
                txtCode.setText(stock.getId());
                txtName.setText(stock.getName());
                txtUnitprice.setText(String.valueOf(stock.getUnitPrice()));
                txtQty.setText(String.valueOf(stock.getQty()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
        @FXML
        void mouseClickOnAction(MouseEvent event) {
                StockTm selectedItem = tblStock.getSelectionModel().getSelectedItem();
                txtCode.setText(selectedItem.getId());
                txtName.setText(selectedItem.getName());
                txtUnitprice.setText(String.valueOf(selectedItem.getUnitPrice()));
                txtQty.setText(String.valueOf(selectedItem.getQty()));
        }

    }


