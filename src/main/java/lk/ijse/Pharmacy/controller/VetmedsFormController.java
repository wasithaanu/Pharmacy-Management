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
import javafx.scene.text.Text;
import lk.ijse.Pharmacy.model.Customer;
import lk.ijse.Pharmacy.model.Order;
import lk.ijse.Pharmacy.model.Stock;
import lk.ijse.Pharmacy.model.Vetmeds;
import lk.ijse.Pharmacy.model.tm.CustomerTm;
import lk.ijse.Pharmacy.model.tm.VetmedsTm;
import lk.ijse.Pharmacy.repository.CustomerRepo;
import lk.ijse.Pharmacy.repository.OrdersRepo;
import lk.ijse.Pharmacy.repository.StockRepo;
import lk.ijse.Pharmacy.repository.VetmedsRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class VetmedsFormController {

    @FXML
    private ComboBox<?> cmbName;

    @FXML
    private ComboBox<?> cmbOid;

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
    private TableColumn<?, ?> colQty;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private Text root;

    @FXML
    private TableView<VetmedsTm> tblVetmeds;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblItemCode;



    private ObservableList<VetmedsTm> vetmedsList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        loadNextOrderId();
        setDate();
        //getCustomerIds();
        // getItemCodes();
    }


    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colId.setCellValueFactory(new PropertyValueFactory<>("Oid"));
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
                    vetmeds.getOId(),
                    vetmeds.getItemCode(),
                    vetmeds.getName(),
                    vetmeds.getDesc(),
                    vetmeds.getUnitPrice(),
                    vetmeds.getQty(),
                    vetmeds.getAmount()
            );

            tmList.add(vetmedsTm);
        }
        tblVetmeds.setItems(tmList);
        VetmedsTm selectedItem = tblVetmeds.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    private void loadNextOrderId() {
        try {
            String currentId = VetmedsRepo.currentId();
            String nextId = nextId(currentId);

            //lblOrderId.setText(nextId);
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

    private void setDate() {
        LocalDate now = LocalDate.now();
       // lblOrderDate.setText(String.valueOf(now));
    }
    @FXML
    void QtyOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddMedsOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String oId = (String) cmbOid.getValue();
        String name = (String) cmbName.getValue();
        String itemCode = lblItemCode.getText();
        String desc = lblDesc.getText();
        double unitPrice = Double.parseDouble(lblPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double amount = qty * unitPrice;
        var btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblVetmeds.getSelectionModel().getSelectedIndex();
                vetmedsList.remove(selectedIndex);

                tblVetmeds.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblVetmeds.getItems().size(); i++) {
           if (code.equals(colItemCode.getCellData(i))) {
                qty += vetmedsList.get(i).getQty();
                amount = unitPrice * qty;

                vetmedsList.get(i).setQty(qty);
                vetmedsList.get(i).setAmount(amount);

                tblVetmeds.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }

        VetmedsTm vetmedsTm = new VetmedsTm(code, name, desc, unitPrice, itemCode, amount, qty, oId, amount, btnRemove);

        vetmedsList.add(vetmedsTm);

        tblVetmeds.setItems(vetmedsList);
        txtQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        netAmount = 0;
        for (int i = 0; i < tblVetmeds.getItems().size(); i++) {
            netA += (double) colAmount.getCellData(i);
        }
        lblAmount.setText(String.valueOf(netTotal));
    }



    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnPayOnAction(ActionEvent event) {

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
                lblName.setText(String.valueOf(stock.getName()));
                lblItemCode.setText(String.valueOf(stock.getId()));
                lblPrice.setText(String.valueOf(stock.getUnitPrice()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtQty.requestFocus();
    }

    @FXML
    void cmbOidOnAction(ActionEvent event) {
        String oId = (String) cmbOid.getValue();

        try {
            Order order = OrdersRepo.searchById(oId);

            lblCustomerName.setText(orders.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}



