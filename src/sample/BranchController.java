package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BranchController implements Initializable {
    @FXML
    private TableView<Branch> branchTable;
    @FXML private TableColumn<Branch, String> idColumn;
    @FXML private TableColumn<Branch, String> branchNameColumn;
    @FXML private TableColumn<Branch, Button> productViewerCol;
    @FXML private TextField idField;
    @FXML private TextField productNameField;
    @FXML private TextField searchField;


    public void backToHome(ActionEvent event) throws IOException {
        Parent productParent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene productScene = new Scene(productParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(productScene);
        window.show();
    }

    public void addButton() throws SQLException {
        Branch newBranch = new Branch(idField.getText(), productNameField.getText());
        int total = branchTable.getItems().size();
        String size = String.valueOf(total + 1);
        sqlQuery app = new sqlQuery();
        app.insertInBranch(size, productNameField.getText());
        branchTable.getItems().add(newBranch);
    }

    public void deleteButton() throws SQLException {
        ObservableList<Branch> allBranch = branchTable.getItems();
        ObservableList<Branch> selectedRows = branchTable.getSelectionModel().getSelectedItems();
        Branch index = branchTable.getSelectionModel().getSelectedItem();
        String branchName = index.getBranchName();

        allBranch.removeAll(selectedRows);
        sqlQuery app = new sqlQuery();
        app.deleteInBranch(branchName);
        System.out.println(branchName + " has been deleted.");
    }

    public void editButton() throws SQLException {
        Branch branchSelected = branchTable.getSelectionModel().getSelectedItem();
        String productName = branchSelected.getBranchName();
        String id = branchSelected.getID();
        sqlQuery app = new sqlQuery();
        app.editInBranch(productName,id);
        System.out.println(productName + " has been update onto the DB");
    }

    public void editBranchName(TableColumn.CellEditEvent editedCell) {
        Branch branchSelected = branchTable.getSelectionModel().getSelectedItem();
        String old = editedCell.getOldValue().toString();
        branchSelected.setBranchName(editedCell.getNewValue().toString());
        System.out.println(old);
    }



    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        branchNameColumn.setCellValueFactory(new PropertyValueFactory("branchName"));
        productViewerCol.setCellValueFactory(new PropertyValueFactory("productView"));

        try {
            branchTable.setItems(getBranch());
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        branchTable.setEditable(true);
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        branchNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        branchTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        try {
            FilteredList<Branch> filteredData = new FilteredList<>(getBranch(), b -> true);
            searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Product.getBranchName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Product.getID().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            }));

            SortedList<Branch> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(branchTable.comparatorProperty());
            branchTable.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Branch> getBranch() throws SQLException {
        ObservableList<Branch> branches = FXCollections.observableArrayList();
        Connection connect = ConnectDB.connect();
        Statement statement = connect.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("SELECT * FROM branch");

        while(rs.next()) {
            String id = String.valueOf(rs.getInt("branch_id"));
            String bName = rs.getString("branch_location");
            branches.add(new Branch(id, bName));
        }

        return branches;
    }

}

