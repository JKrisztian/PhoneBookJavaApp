/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Krisztián
 */
public class ViewController implements Initializable {
    
//<editor-fold defaultstate="collapsed" desc="ClassVariable">
    private final String MENU_EXIT = "Kilépés";
    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Listázás";
    private final String MENU_EXPORT = "Exportálás";
    
    
    @FXML
            TableView table;
    @FXML
            TextField inputLastName;
    @FXML
            TextField inputFirstName;
    @FXML
            TextField inputEmail;
    @FXML
            TextField inputExportFileName;
    @FXML
            Button exportButton;
    @FXML
            Button addContact;
    @FXML
            StackPane menuPane;
    @FXML
            Pane contactPane;
    @FXML
            Pane exportPane;    
    @FXML
            private Label label;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="TableData">
    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Szabó","Bálint","szabo4545s@email.com"),
                    new Person("Szántó","Benedek","szántobende@email.com"),
                    new Person("Szalai","Boldizsár","szalabol@email.com"));
//</editor-fold>
    @FXML
    private void addNewContact (ActionEvent event){
        String email = inputEmail.getText();
        if(email.length() > 3 && email.contains("@") && email.contains(".")){
            data.add(new Person(inputFirstName.getText(), inputLastName.getText(), email));
            inputFirstName.clear();
            inputLastName.clear();
            inputEmail.clear();
        }
    }
    
    @FXML
    private void addNewPdf (ActionEvent event){
        String filename = inputExportFileName.getText();
        //whitespace karakterek szűrése
        filename = filename.replace("\\s+", "");
        
        if( filename != null && !filename.equals("")){
            PdfGenerator pdf = new PdfGenerator();
            pdf.pdfGeneration(filename, data);
        }
        inputExportFileName.clear();
        
    }
    
    public void setTableData(){
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLastName(
                                        t.getNewValue());
                    }
                }
        );
        
        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        
        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setFirstName(t.getNewValue());
                    }
                }
        );
        
        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(150);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        
        emailCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    }
            }
        );
        
        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol);
        table.setItems(data);
    }
    
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
        
        Node contactNode = new ImageView(new Image(getClass().getResourceAsStream("/contact.png")));
        nodeItemA.setExpanded(true);
        
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST,contactNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT);
        
        nodeItemA.getChildren().addAll(nodeItemA1,nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA,nodeItemB);
        
        menuPane.getChildren().add(treeView);
        
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                
                if(null != selectedMenu){
                    switch (selectedMenu){
                        case MENU_CONTACTS:
                            try {
                                selectedItem.setExpanded(true);
                            } catch (Exception e) {
                            }
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                    }
                    System.out.println("Kivalasztott menu : "+selectedMenu);
                }
                
            }
        });
                
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        
        setMenuData();

    }    

    


    
    
}
