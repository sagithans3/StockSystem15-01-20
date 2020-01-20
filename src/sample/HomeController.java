package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    public void changeToProduct(ActionEvent event) throws IOException {
        Parent productParent = FXMLLoader.load(getClass().getResource("productpage.fxml"));
        Scene productScene = new Scene(productParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(productScene);
        window.show();
    }

    public void changeToWholesaler(ActionEvent event) throws IOException {
        Parent productParent = FXMLLoader.load(getClass().getResource("wholesalerpage.fxml"));
        Scene productScene = new Scene(productParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(productScene);
        window.show();
    }

    public void changeToStorage(ActionEvent event) throws IOException {
        Parent productParent = FXMLLoader.load(getClass().getResource("storagepage.fxml"));
        Scene productScene = new Scene(productParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(productScene);
        window.show();
    }

    public void changeToBranch(ActionEvent event) throws IOException {
        Parent branchParent = FXMLLoader.load(getClass().getResource("branchPage.fxml"));
        Scene branchScene = new Scene(branchParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(branchScene);
        window.show();
    }

}

