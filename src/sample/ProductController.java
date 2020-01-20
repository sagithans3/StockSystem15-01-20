package sample;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class ProductController implements Initializable {
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> idColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> productQuantityColumn;
    @FXML private TableColumn<Product, String> productPriceColumn;
    @FXML private TextField idField;
    @FXML private TextField productNameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField searchField;


    public void backToHome(ActionEvent event) throws IOException {
        Parent productParent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene productScene = new Scene(productParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(productScene);
        window.show();
    }

    public void addButton() throws SQLException {
        Product newProduct = new Product(idField.getText(), productNameField.getText(), quantityField.getText(), priceField.getText());
        int total = productTable.getItems().size();
        String size = String.valueOf(total + 1);
        sqlQuery app = new sqlQuery();
        app.insertInProduct(size, productNameField.getText(), quantityField.getText(), priceField.getText());
        productTable.getItems().add(newProduct);
    }

    public void deleteButton() throws SQLException {
        ObservableList<Product> allProduct = productTable.getItems();
        ObservableList<Product> selectedRows = productTable.getSelectionModel().getSelectedItems();
        Product index = productTable.getSelectionModel().getSelectedItem();
        String productName = index.getProductName();
        String quantity = index.getQuantity();
        allProduct.removeAll(selectedRows);
        sqlQuery app = new sqlQuery();
        app.deleteInProduct(productName);
        System.out.println(productName + quantity + " has been deleted.");
    }

    public void editButton() throws SQLException {
        Product productSelected = productTable.getSelectionModel().getSelectedItem();
        String productName = productSelected.getProductName();
        String quantity = productSelected.getQuantity();
        String price = productSelected.getPrice();
        String id = productSelected.getID();
        sqlQuery app = new sqlQuery();
        app.editInProducts(productName, quantity, price, id);
        System.out.println(productName + "," + quantity + "," + price + " has been update onto the DB");
    }

    public void editProductName(CellEditEvent editedCell) {
        Product productSelected = productTable.getSelectionModel().getSelectedItem();
        String old = editedCell.getOldValue().toString();
        productSelected.setProductName(editedCell.getNewValue().toString());
        System.out.println(old);
    }

    public void editQuantity(CellEditEvent editedCell) {
        Product quantitySelected = productTable.getSelectionModel().getSelectedItem();
        quantitySelected.setQuantity(editedCell.getNewValue().toString());
    }

    public void editPrice(CellEditEvent editedCell) {
        Product productSelected = productTable.getSelectionModel().getSelectedItem();
        String old = editedCell.getOldValue().toString();
        productSelected.setPrice(editedCell.getNewValue().toString());
        System.out.println(old);
    }

    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory("productName"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));

        try {
           productTable.setItems(getProducts());
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

        productTable.setEditable(true);
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        try {
            FilteredList<Product> filteredData = new FilteredList<>(getProducts(), b -> true);
                searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(Product -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (Product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (Product.getQuantity().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (Product.getPrice().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (Product.getID().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    });
                }));

            SortedList<Product> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Product> getProducts() throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        Connection connect = ConnectDB.connect();
        Statement statement = connect.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("SELECT * FROM products");

        while(rs.next()) {
            String id = String.valueOf(rs.getInt("product_id"));
            String pName = rs.getString("product_name");
            String quantity = rs.getString("product_quantity");
            String cost = rs.getString("product_cost");
            products.add(new Product(id, pName, quantity, cost));
        }

        return products;
    }
}



