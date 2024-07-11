package lk.ijse.Pharmacy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Pharmacy.bo.BOFactory;
import lk.ijse.Pharmacy.bo.custom.RegisterBO;
import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.dto.AdminDTO;
import lk.ijse.Pharmacy.dto.CustomerDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    RegisterBO registerBO = (RegisterBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REGISTER);

    @FXML
    void registerOnAction(ActionEvent event) {
        String user_id = txtUserId.getText();
        String name = txtUserName.getText();
        String pw = txtPassword.getText();

        saveUser(user_id, pw);
    }

    private void saveUser(String userId , String pw) {
        AdminDTO adminDTO = new AdminDTO(userId,pw);

        try {
            boolean isSaved = registerBO.regAdmin(adminDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Admin saved!").show();
                navigateToTheDashboard();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        }

    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene( scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }
}
