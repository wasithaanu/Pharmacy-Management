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
import lk.ijse.Pharmacy.bo.custom.CustomerBO;
import lk.ijse.Pharmacy.bo.custom.LoginBO;
import lk.ijse.Pharmacy.db.DbConnection;
import lk.ijse.Pharmacy.dto.AdminDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    public AnchorPane root;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    public void btnLoginOnAction(ActionEvent event) {
        String userId = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT username, password FROM admin WHERE username = ?";
        try {
            AdminDTO adminDTO= loginBO.login(userId);


            if(adminDTO.getPassword().equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void linkRegisterOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/registration_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();
    }
}


