package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class Branch {
    public Branch(String id, String branchName) {
        this.id = new SimpleStringProperty(id);
        this.branchName = new SimpleStringProperty(branchName);
        this.productView = new Button("View Products");
    }

    private SimpleStringProperty id, branchName;
    private Button productView;

    public String getBranchName() {
        return branchName.get();
    }

    public SimpleStringProperty branchNameProperty() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName.set(branchName);
    }

    public Button getProductView() {
      return productView;
    }

    public void setProductView(Button productView){this.productView = productView; }

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
