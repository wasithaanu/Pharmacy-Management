package lk.ijse.Pharmacy.controller;

//package lk.ijse.Pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Pharmacy.model.*;
import lk.ijse.Pharmacy.model.tm.VetmedsTm;
import lk.ijse.Pharmacy.repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VetmedsFormController {

    @FXML
    private ComboBox<String> cmbName;

    @FXML
    private ComboBox<String> cmbOid;

    @FXML
    private TextField txtCode;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<VetmedsTm, Integer> colQty;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;


    @FXML
    private AnchorPane rootVetmeds;

    @FXML
    private TableView<VetmedsTm> tblVetmeds;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblItemCode;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtVcode;





    private String nextId;



    private ObservableList<VetmedsTm> vetmedsList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        setDate();
        loadVetmedsTable();
        getOrderIds();
        getStockNames();

    }

    private void getStockNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = StockRepo.getName();

            for (String id : idList) {
                obList.add(id);
            }
            cmbName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getOrderIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = OrdersRepo.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbOid.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colId.setCellValueFactory(new PropertyValueFactory<>("oId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }
    private void loadVetmedsTable() {
        ObservableList<VetmedsTm> tmList = FXCollections.observableArrayList();

        for (VetmedsTm vetmeds : vetmedsList) {
            VetmedsTm vetmedsTm = new VetmedsTm(
                    vetmeds.getCode(),
                    vetmeds.getName(),
                    vetmeds.getDesc(),
                    vetmeds.getUnitPrice(),
                    vetmeds.getItemCode(),
                    vetmeds.getAmount(),
                    vetmeds.getQty(),
                    vetmeds.getOId(),
                    vetmeds.getDate()
            );

            tmList.add(vetmedsTm);
        }
        tblVetmeds.setItems(tmList);
        VetmedsTm selectedItem = tblVetmeds.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    /*private void loadNextOrderId() {
        try {
            String currentId = VetmedsRepo.currentId();
            String nextId = nextId(currentId);

            cmbOid.setValue(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("V");
            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);    //2
            return "V" + ++id;

        }
        return "O1";
    }
*/
    private void setDate() {
        LocalDate now = LocalDate.now();
       // lblOrderDate.setText(String.valueOf(now));
    }
    @FXML
    void QtyOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddMedsOnAction(ActionEvent event) {
        String code = txtVcode.getText();
        String oId = (String) cmbOid.getValue();
        String name = (String) cmbName.getValue();
        String itemCode = lblItemCode.getText();
        String desc = txtDesc.getText();
        double unitPrice = Double.parseDouble(lblPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double amount = qty * unitPrice;
        String date = txtDate.getText();



        for (int i = 0; i < tblVetmeds.getItems().size(); i++) {
           if (code.equals(colItemCode.getCellData(i))) {
                qty += vetmedsList.get(i).getQty();
                amount = unitPrice * qty;

                vetmedsList.get(i).setQty(qty);
                vetmedsList.get(i).setAmount(amount);

                tblVetmeds.refresh();
                calculateNetTotal();


            }
        }

        VetmedsTm vetmedsTm = new VetmedsTm(code, name, desc, unitPrice, itemCode, amount, qty, oId, date);
        System.out.println(vetmedsTm+"ammt");
        vetmedsList.add(vetmedsTm);

        tblVetmeds.setItems(vetmedsList);

        calculateNetTotal();
    }

    private void calculateNetTotal() {
        double netAmount = 0;
        for (int i = 0; i < tblVetmeds.getItems().size(); i++) {
            netAmount += (double) colAmount.getCellData(i);
        }
        lblAmount.setText(String.valueOf(netAmount));
    }



    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtVcode.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnPayOnAction(ActionEvent event) {
         String vCode = colCode.getText();
         String stockId = colItemCode.getText();
         String qty =colQty.getText();

        var     stock =new StockUpdate(stockId,qty);
        System.out.println(stock+"stock eka ");

        List<VetmedDetails> odList = new ArrayList<>();
        for (int i = 0; i < tblVetmeds.getItems().size(); i++) {
            VetmedsTm tm = vetmedsList.get(i);

            VetmedDetails od = new VetmedDetails(

                    tm.getCode(),
                    tm.getName(),
                    tm.getDesc(),
                    tm.getUnitPrice(),
                    tm.getItemCode(),
                    tm.getAmount(),
                    tm.getQty(),
                    tm.getOId(),
                    tm.getDate()
            );
            odList.add(od);
        }

        StockAndVetmeds po = new StockAndVetmeds(stock, odList);
        try {
            boolean isPlaced = UpdateAndSaveRepo.updateAndSave(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbNameOnAction(ActionEvent event) {
        String name = (String) cmbName.getValue();
        try {
            Stock stock = StockRepo.searchByName(name);
            if (stock != null) {
                lblItemCode.setText(stock.getId());
                lblPrice.setText(String.valueOf(stock.getUnitPrice()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbOidOnAction(ActionEvent event) {

    }


    }




