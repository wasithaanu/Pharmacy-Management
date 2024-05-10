package lk.ijse.Pharmacy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class VetmedsFormController {

    @FXML
    private ComboBox<?> cmbName;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitprice;

    @FXML
    private Label lblCode;

    @FXML
    private Text root;

    @FXML
    private TableView<?> tblVetmeds;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtQty;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtAddDate(ActionEvent event) {

    }

    @FXML
    void txtAddQty(ActionEvent event) {

    }

}
