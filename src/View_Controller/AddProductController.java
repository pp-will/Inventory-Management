/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wpittman
 */
public class AddProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Label idLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productInvLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label productMinLabel;

    @FXML
    private Label productMaxLabel;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInvField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMinField;

    @FXML
    private TextField productMaxField;

    @FXML
    private Button addProductSearchBtn;

    @FXML
    private TextField addProductSearchField;

    @FXML
    private TableView<Part> productAddPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIDAddColumn;

    @FXML
    private TableColumn<Part, String> partNameAddColumn;

    @FXML
    private TableColumn<Part, Integer> partInvAddColumn;

    @FXML
    private TableColumn<Part, Double> partPriceAddColumn;

    @FXML
    private TableView<Part> addedPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private Button productAddPartBtn;

    @FXML
    private Button productAddSaveBtn;

    @FXML
    private Button productAddDeleteBtn;

    @FXML
    private Button productAddCancelBtn;
    
    static boolean active;
    private String name;
    private int inStock;
    private double price;
    private int min, max;
    private double tempPrice;
    private Product product;
    
    ObservableList<Part> tempList = FXCollections.observableArrayList();
    
    @FXML
    void productAddPartBtnHandler(ActionEvent event) {
        //add selected part to tempList
        Part part = productAddPartsTable.getSelectionModel().getSelectedItem();
         
        tempList.add(part);
        
        //populate addedPartsTable with tempList
        addedPartsTable.setItems(tempList);
        }
    
    @FXML
    void productAddDeleteBtnHandler(ActionEvent event) {
        delete();
    }
    
    @FXML
    void productAddCancelBtnHandler(ActionEvent event) throws IOException{
        try {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Part");
        alert.setHeaderText("This part will not be added");
        alert.setContentText("Press OK to Cancel");
        alert.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) productAddCancelBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("OK PRESSED");
         } else {
            /*Stage stage;
            Parent root;
            stage = (Stage) productAddCancelBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Errror");*/
            alert.close();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void productAddSaveBtnHandler(ActionEvent event) throws IOException {
        //for confirming add product
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Add Product");
        confirm.setHeaderText("This product will be added");
        confirm.setContentText("Press OK to save");
        confirm.getDialogPane().setPrefSize(350, 200);
        Optional<ButtonType> conf = confirm.showAndWait();
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        
            
        
        Random random = new Random();
        int[] id = new int[100];
        for(int i = 0; i < id.length; i++) {
            id[i] = random.nextInt(1000);
        }
        
        name = productNameField.getText();
        
        //ensure productPrice is a double
        try {
            price = parseDouble(productPriceField.getText());
            //product.setPrice(price);
        } catch (NumberFormatException e) {
            alert.setHeaderText("Price must be in the following format:");
            alert.setContentText("X.XX");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            
        }
        
        //ensure productInv is an int
        try {
            inStock = parseInt(productInvField.getText());
            //product.setInStock(inStock);
        } catch (NumberFormatException e) {
            alert.setHeaderText("Inventory must be a number");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        }
        
        //ensure min is an int
        try {
            min = parseInt(productMinField.getText());
            //product.setMin(min);
        } catch (NumberFormatException e) {
            alert.setHeaderText("Min must be a number");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        } 
        
        //ensure max is an int
        try {
            max = parseInt(productMaxField.getText());
            //product.setMax(max);
        } catch (NumberFormatException e) {
            alert.setHeaderText("Max must be a number");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        }
        
        if(max < min) {
            alert.setHeaderText("Max must greater than min");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        } else if (inStock < min || inStock > max || inStock < min && inStock > max) {
            alert.setHeaderText("Inventory must be greater than min, and less than max");
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
        } else {
            int productID = id[random.nextInt(id.length)];
            //product.setProductID(productID);
            for(Part p : tempList) {
                tempPrice += p.getPrice();
                //this.product.addAssociatedPart(p);
                
            }
            if(tempPrice > price) {
                alert.setHeaderText("The price of the product must be greater than the price of the parts");
            alert.setContentText("Price of the parts is $" + tempPrice);
            Optional<ButtonType> alertOK = alert.showAndWait();
            if(alertOK.isPresent() && alertOK.get() == ButtonType.OK) {
                alert.close();
            }
            } else {
            
            
            Product product = new Product(productID, name, price, inStock, min, max, tempList);
            
            Inventory.addProduct(product);
            if(conf.isPresent() && conf.get() == ButtonType.OK) {
                
                Stage stage;
                Parent root;
                stage = (Stage) productAddSaveBtn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                confirm.close();
            }
            
        }
        }
    }
    
    @FXML
    void addProductSearchBtnHandler(ActionEvent event) {
        ObservableList<Part> data = productAddPartsTable.getItems();
        boolean present = false;
        String partString = addProductSearchField.getText();
        try {
            int partNum = Integer.parseInt(partString);
            for(Part p : data) {
                if(p.getPartID() == partNum) {
                    productAddPartsTable.getSelectionModel().select(p);
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
                    productAddPartsTable.getSelectionModel().select(p);
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
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        productAddPartsTable.setItems(Inventory.getAllParts());
        partIDAddColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvAddColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceAddColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

public void delete() {
        ObservableList<Part> data = addedPartsTable.getItems();
        data.remove(addedPartsTable.getSelectionModel().getSelectedItem());
            partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            partInvColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
            
            addedPartsTable.setItems(data);
    }    
    
}
