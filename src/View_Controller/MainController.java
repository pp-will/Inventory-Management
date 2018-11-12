/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wpittman
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label appTitle;

    @FXML
    private Label partsLabel;

    @FXML
    private Button partsSearchBtn;

    @FXML
    private TextField partsText;

    @FXML
    private TableView<Part> partsTable;//allParts

    @FXML
    private TableColumn<Part, Integer> partIDColumn; //reference get methods

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> invLevelColumn;

    @FXML
    private TableColumn<Part, Double> priceColumn;

    @FXML
    private Button productSearchBtn;
    
    @FXML
    private TextField productSearchField;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIDColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInvColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private Button partsAddBtn;

    @FXML
    private Button partsModifyBtn;

    @FXML
    private Button partsDeleteBtn;

    @FXML
    private Button productsAddBtn;

    @FXML
    private Button productsModifyBtn;

    @FXML
    private Button productsDeleteBtn;

    @FXML
    private Button exitBtn;
    
    static boolean active;
    
    //reference tables
    
    @FXML
    private void onExitBtnHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void partsAddBtnHandler(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) partsAddBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AddPartController controller = loader.getController();
        
    }
    
    @FXML
    void partsModifyBtnHandler(ActionEvent event) throws IOException {
        Stage stage; 
        Parent root;       
        stage=(Stage) partsModifyBtn.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
        root =loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ModifyPartController controller = loader.getController();
        Part part = partsTable.getSelectionModel().getSelectedItem();
        controller.setPart(part);
        delete();
       }
    
    @FXML
    void partsDeleteBtnHandler(ActionEvent event) {
        delete();
    }
    
    @FXML
    void productsDeleteBtnHandler(ActionEvent event) {
        deleteProduct();
    }
    
    @FXML
    void partsSearchBtnHandler(ActionEvent event) {
        ObservableList<Part> data = partsTable.getItems();
        boolean present = false;
        String partString = partsText.getText();
        try {
            int partNum = Integer.parseInt(partString);
            for(Part p : data) {
                if(p.getPartID() == partNum) {
                    System.out.println("found " + p.getName());
                    partsTable.getSelectionModel().select(p);
                    present = true;
                }   
            }
            
            if(present == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Not Found");
                    alert.setHeaderText("The part you searched for was not found");
                    alert.getDialogPane().setPrefSize(350, 200);
                    alert.showAndWait();
            }
            
        } catch (NumberFormatException e) {
            for(Part p : data) {
                partString = partString.toLowerCase();
                String partName = p.getName().toLowerCase();
                if(partName.equals(partString)) {
                    partsTable.getSelectionModel().select(p);
                    present = true;
                }
            }
            if(present == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Not Found");
                    alert.setHeaderText("The part you searched for was not found");
                    alert.getDialogPane().setPrefSize(350, 200);
                    alert.showAndWait();
            }
        }
    }
    
    @FXML
    void productSearchBtnHandler(ActionEvent event) {
        ObservableList<Product> data = productsTable.getItems();
        boolean present = false;
        String productString = productSearchField.getText();
        try {
            int productNum = Integer.parseInt(productString);
            for(Product p : data) {
                if(p.getProductID() == productNum) {
                    productsTable.getSelectionModel().select(p);
                    present = true;
                }
            }
            
            if(present == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product Not Found");
                    alert.setHeaderText("The product you searched for was not found");
                    alert.getDialogPane().setPrefSize(350, 200);
                    alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            for(Product p : data) {
                productString = productString.toLowerCase();
                String productName = p.getName().toLowerCase();
                if(productName.equals(productString)) {
                    productsTable.getSelectionModel().select(p);
                    present = true;
                }
            }
            
            if(present == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product Not Found");
                    alert.setHeaderText("The product you searched for was not found");
                    alert.getDialogPane().setPrefSize(350, 200);
                    alert.showAndWait();
            }
        }
    }
    
    @FXML
    void productsAddBtnHandler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) productsAddBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AddProductController controller = loader.getController();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!active) {
        Inhouse inhouse1 = new Inhouse(0, "Tires", 1.99, 5, 1, 8, 123);
        Inhouse inhouse2 = new Inhouse(1, "Seat", 2.50, 7, 2, 6, 1232);
        Inhouse inhouse3 = new Inhouse(2, "Handlebars", 3.99, 4, 3, 7, 4324);
        
        Outsourced outsourced1 = new Outsourced(3, "Wheels", 4.99, 3, 1, 9, "Parts Inc.");
        Outsourced outsourced2 = new Outsourced(4, "Deck", 3.50, 3, 2, 6, "Parts Inc.");
        Outsourced outsourced3 = new Outsourced(5, "Trucks", 6.00, 4, 3, 9, "Parts Inc");
        
        Inhouse inhouse4 = new Inhouse(6, "Spring", 0.99, 5, 2, 10, 534);
        Inhouse inhouse5 = new Inhouse(7, "Pogo Body", 5.00, 4, 1, 6, 5883);
        
        Inventory.allParts.add(0, inhouse1);
        Inventory.allParts.add(1, inhouse2);
        Inventory.allParts.add(2, inhouse3);
        Inventory.allParts.add(3, outsourced1);
        Inventory.allParts.add(4, outsourced2);
        Inventory.allParts.add(5, outsourced3);
        Inventory.allParts.add(6, inhouse4);
        Inventory.allParts.add(7, inhouse5);
        
        //building ObservableLists for Products
        ObservableList<Part> prod1 = FXCollections.observableArrayList();
        ObservableList<Part> prod2 = FXCollections.observableArrayList();
        ObservableList<Part> prod3 = FXCollections.observableArrayList();
        
        //add parts to list
        prod1.add(0, inhouse1);
        prod1.add(1, inhouse2);
        prod1.add(2, inhouse3);
        prod2.add(0, outsourced1);
        prod2.add(1, outsourced2);
        prod2.add(2, outsourced3);
        prod3.add(0, inhouse3);
        prod3.add(1, inhouse4);
        prod3.add(2, inhouse5);
        
        //initialize product
        Product product1 = new Product(0, "Bike", 40.00, 6, 1, 9, prod1);
        Product product2 = new Product(1, "Skateboard", 20.00, 7, 1, 7, prod2);
        Product product3 = new Product(2, "Pogo Stick", 30.00, 4, 1, 8, prod3);
        
        Inventory.products.add(0, product1);
        Inventory.products.add(1, product2);
        Inventory.products.add(2, product3);
        
        
        active = true;
        }
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        invLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        productsTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
    }    
    
    public void delete() {
        ObservableList<Part> data = partsTable.getItems();
        data.remove(partsTable.getSelectionModel().getSelectedItem());
            partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            invLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
            
            partsTable.setItems(data);
    }
    
    public void deleteProduct() {
        ObservableList<Product> data = productsTable.getItems();
        data.remove(productsTable.getSelectionModel().getSelectedItem());
            productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            productInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }
    
}
