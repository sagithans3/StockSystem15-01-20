package sample;

import javafx.beans.property.SimpleStringProperty;


public class Product {
    public Product(String id, String productName, String quantity, String price) {
        this.id = new SimpleStringProperty(id);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleStringProperty(quantity);
        this.price = new SimpleStringProperty(price);
    }

    private SimpleStringProperty productName, quantity, price;
    private SimpleStringProperty id;

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getID() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setID(String id) {
        this.id.set(id);
    }

}
