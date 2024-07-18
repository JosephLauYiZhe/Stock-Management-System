import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class StockManagement extends Application {

	private static History[] history = new History[20];
	private static int historyIndex = 0;
	private static final int NOTIFICATION_THRESHOLD = 10; // Specify the threshold quantity for notification
    @Override
    public void start(Stage primaryStage) {
        // Initialize objects
    	
   	
    	UserInfo userInfo = new UserInfo();
    	ArrayList<Product> products = new ArrayList<>();


        // Set up JavaFX UI components
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(20));
        
        LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		Label dateNtime = new Label(now.format(formatter));
        
        Label welcomeLabel = new Label("Welcome to Alimama Stock Management");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        String groupMembers = ("Joseph Lau Yi Zhe\nKee Wei Chuan\nLee Chun Siang\nLow Zi Cheng");
        Text displayMember = new Text("Group members:\n" + groupMembers);       
        
        Label nameLabel = new Label("Enter your full name:");
        TextField nameTextField = new TextField();
 
        nameTextField.setOnAction(event -> {
            // Get user's full name from text field
            String fullName = nameTextField.getText();
            if (!fullName.isEmpty()) {
                // Set user's name and initialize stock management
                userInfo.setName(fullName);
                // Proceed to main menu                
                getMaxProducts(primaryStage, products, userInfo);
               
            } else {
                // Notify user to enter a name
                nameLabel.setText("Please enter your full name:");
            }
        });

        // Add components to the layout
        root.getChildren().addAll(dateNtime, welcomeLabel, displayMember, nameLabel, nameTextField);
        
        // Set up the scene and display the stage
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stock Management System");
        primaryStage.show();
    }

    
    private static void getMaxProducts(Stage primaryStage, ArrayList<Product> products, UserInfo userInfo) {
    	
    	 primaryStage.close();
    	 
    	 TextField maxProductsTextField = new TextField();
    	 Label errorLabel = new Label();
    	 errorLabel.setTextFill(Color.RED);
    	 VBox root = new VBox();
    	 root.setSpacing(10);
    	 root.setPadding(new Insets(20));
    	 Label titleLabel = new Label("Maximum Products");
    	 titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    	 Label promptLabel = new Label("Enter the maximum number of products to store in the system: \n(enter 0 to exit program)");

    	 maxProductsTextField.setOnAction(event -> {
    	     String input = maxProductsTextField.getText();
    	     try {
    	         int maxProducts = Integer.parseInt(input);
    	         if (maxProducts == 0) {
    	        	displayInfo(userInfo);
    	         }
    	         else if (maxProducts > 0) {
    	        	 
    	             primaryStage.close();
    	             

    	             addProduct(primaryStage, maxProducts, products,  userInfo);
    	         } else  if (maxProducts < 0 ){
    	             errorLabel.setText("Invalid input. Please enter a positive value.");
    	             maxProductsTextField.clear();
                 }
    	         } catch (NumberFormatException e) {
    	            errorLabel.setText("Invalid input. Please enter a valid integer.");
    	            maxProductsTextField.clear();
    	        }
    	    });

    	 root.getChildren().addAll(titleLabel, promptLabel, maxProductsTextField, errorLabel);
         Scene scene = new Scene(root, 500, 200);
         primaryStage.setScene(scene);
         primaryStage.setTitle("Maximum Products");
         primaryStage.show();
         
        

    }
    

    private static void addProduct(Stage primaryStage, int maxProducts, ArrayList<Product> products, UserInfo userInfo) {
        Stage productTypeStage = new Stage();
        while (products.size() < maxProducts) {

            VBox vb = new VBox();
            vb.setSpacing(10);
            vb.setPadding(new Insets(20));

            Label productTypeTitleLabel = new Label("Enter the number to select Product Type:");
            productTypeTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Label productNumberLabel = new Label("Product " + (products.size() + 1) + " of " + maxProducts);

            Text text = new Text("1. Refrigerator\n2. TV\n3. Laptop\n0. Exit");

            TextField productTypeField = new TextField();
            productTypeField.setPromptText("1 for Refrigerator, 2 for TV, 3 for Laptop");

            Label errorLabel = new Label();
            errorLabel.setTextFill(Color.RED);

            productTypeField.setOnAction(event -> {
                String input = productTypeField.getText();
                try {
                    int choice = Integer.parseInt(input);
                    productNumberLabel.setText("Product " + (products.size() + 1) + " of " + maxProducts);
                    switch (choice) {
                        case 1:
                            addRefrigerator(maxProducts, productTypeStage, products);
                            break;
                        case 2:
                            addTV(maxProducts, productTypeStage, products);
                            break;
                        case 3:
                            addLaptop(maxProducts, productTypeStage, products);
                            break;
                        case 0:
                            displayInfo(userInfo);
                            break;
                        default:
                            errorLabel.setText("Only numbers 1, 2, or 3 allowed!");
                            productTypeField.clear();
                            break;
                    }
                } catch (NumberFormatException e) {
                    errorLabel.setText("Invalid input. Please enter a valid integer.");
                    productTypeField.clear();
                }
            });


            vb.getChildren().addAll(productTypeTitleLabel, productNumberLabel, text, productTypeField, errorLabel);
            Scene productTypeScene = new Scene(vb, 700, 300);
            productTypeStage.setScene(productTypeScene);
            productTypeStage.setTitle("Product Type Selection");
            productTypeStage.showAndWait();
        }

        primaryStage.close();
        displayMenu(products, userInfo);

    }

    
        
    private static void displayMenu(ArrayList<Product> products, UserInfo userInfo) {
    	

    	Label errorLabel = new Label();
    	errorLabel.setTextFill(Color.RED);
    	 
        // Create new stage for displaying menu
        Stage menuStage = new Stage();

        // Set up JavaFX UI components for displaying menu
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label menuLabel = new Label("Menu Options:");
        menuLabel.setStyle("-fx-font-weight:bold; -fx-font-size: 20px;");

        Text menuText = new Text();
      //  menuText.setEditable(false);
        menuText.setText("1. View Products\n2. Add Stock\n3. Deduct Stock\n4. Discontinue Product\n0. Exit");

        Label choiceLabel = new Label("Please enter a menu option:");
        TextField choiceTextField = new TextField();

       
        choiceTextField.setOnAction(event -> {
            String choice = choiceTextField.getText();
            //while (!done) {
            	try {
            		int inChoice = Integer.parseInt(choice);
            		if(inChoice < 0 || inChoice > 4) {
            			errorLabel.setText("Invalid input. Please enter a valid number.");          			
            			choiceTextField.clear();
            		}
            		else {
            			menuStage.close();
	            		switch (inChoice) {
	            			case 1: 
	            				//menuStage.close();
	            				viewProducts(products, userInfo);
	            			//	done = true;
	            				break;
	            			
	            			case 2:
	            				//menuStage.close();
	            				addStock(products, userInfo);
	            				//done = true;
	            				break;
	            				
	            			case 3:
	            			   // menuStage.close();
	            				deductStock(products, userInfo);
	            				break;
	            				
	            			case 4:
	            				//menuStage.close();
	            				setStatus(products, userInfo);
	            				break;
	            				
	            			case 0:
	            				//menuStage.close();
	            				displayInfo(userInfo);
	            				//System.exit(0);
	            				break;
	            				
	            			default:
		                        errorLabel.setText("Invalid number!");
		                        choiceTextField.clear();
		                        
		                        break;
	            		}
            		}
    	               
    	                // Clear the text field after submitting the choice
    	            choiceTextField.clear();
    	            
            	}
            	 catch(NumberFormatException e){
 	            	
 	            	   errorLabel.setText("Invalid input. Please enter a valid integer.");
 	    	           choiceTextField.clear();
	           
	            }
          //  }
        });

        // Add components to the layout
        root.getChildren().addAll(menuLabel, menuText, choiceLabel, choiceTextField, errorLabel);

        // Set up the scene and display the stage
        Scene scene = new Scene(root, 400, 305);
        menuStage.setScene(scene);
        menuStage.setTitle("Menu Options");
        menuStage.show();
    }
    
    
    private static void viewProducts(ArrayList<Product> products, UserInfo userInfo) {
    	
        Stage viewProductsStage = new Stage();
        VBox vb = new VBox(10);
        vb.setPadding(new Insets(20));

        Label titleLabel = new Label("Select Category to View Products");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Radio buttons for product categories
        ToggleGroup group = new ToggleGroup();
        RadioButton rbRefrigerator = new RadioButton("Refrigerator");
        rbRefrigerator.setToggleGroup(group);
        rbRefrigerator.setUserData("Refrigerator");

        RadioButton rbTV = new RadioButton("TV");
        rbTV.setToggleGroup(group);
        rbTV.setUserData("TV");

        RadioButton rbLaptop = new RadioButton("Laptop");
        rbLaptop.setToggleGroup(group);
        rbLaptop.setUserData("Laptop");

        HBox radioBox = new HBox(10, rbRefrigerator, rbTV, rbLaptop);

        // TableView setup
        TableView<Product> productTable = new TableView<>();
        productTable.setEditable(false);
        productTable.setPrefHeight(350);

        // Common columns
        TableColumn<Product, String> itemNumberColumn = new TableColumn<>("Item Number");
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<Product, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Discontinued"));

       
        // Dynamic column setup and product filtering
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productTable.getColumns().clear();  // Clear existing columns
                String category = newValue.getUserData().toString();

                // Add common columns first
                productTable.getColumns().addAll(itemNumberColumn, nameColumn, statusColumn, priceColumn, quantityColumn);

                // Category-specific columns
                switch (category) {
                    case "Laptop":
                        TableColumn<Product, Double> screenSizeColumn = new TableColumn<>("Screen Size");
                        screenSizeColumn.setCellValueFactory(new PropertyValueFactory<>("screenSize"));
                        TableColumn<Product, String> processorColumn = new TableColumn<>("Processor");
                        processorColumn.setCellValueFactory(new PropertyValueFactory<>("processor"));
                        TableColumn<Product, String> ramColumn = new TableColumn<>("RAM");
                        ramColumn.setCellValueFactory(new PropertyValueFactory<>("ram"));
                        TableColumn<Product, String> storageColumn = new TableColumn<>("Storage");
                        storageColumn.setCellValueFactory(new PropertyValueFactory<>("storage"));
                        productTable.getColumns().addAll(screenSizeColumn, processorColumn, ramColumn, storageColumn);
                        break;
                    case "Refrigerator":
                        TableColumn<Product, String> doorDesignColumn = new TableColumn<>("Door Design");
                        doorDesignColumn.setCellValueFactory(new PropertyValueFactory<>("doorDesign"));
                        TableColumn<Product, String> colorColumn = new TableColumn<>("Color");
                        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
                        TableColumn<Product, Double> capacityColumn = new TableColumn<>("Capacity");
                        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
                        productTable.getColumns().addAll(doorDesignColumn, colorColumn, capacityColumn);
                        break;
                    case "TV":
                        TableColumn<Product, String> typeColumn = new TableColumn<>("Screen Type");
                        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                        TableColumn<Product, String> resolutionColumn = new TableColumn<>("Resolution");
                        resolutionColumn.setCellValueFactory(new PropertyValueFactory<>("resolution"));
                        TableColumn<Product, Double> displaySizeColumn = new TableColumn<>("Display Size");
                        displaySizeColumn.setCellValueFactory(new PropertyValueFactory<>("displaySize"));
                        productTable.getColumns().addAll(typeColumn, resolutionColumn, displaySizeColumn);
                        break;
                }

                // Filter products for the selected category and active status
                ObservableList<Product> filteredList = FXCollections.observableArrayList();
                for (Product product : products) {
                    if (product.getClass().getSimpleName().equals(category)) {
                        filteredList.add(product);
                    }
                }
                productTable.setItems(filteredList);
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            viewProductsStage.close();
            displayMenu(products, userInfo);
        });

        vb.getChildren().addAll(titleLabel, radioBox, productTable, backButton);

        Scene scene = new Scene(vb, 700, 450);
        viewProductsStage.setScene(scene);
        viewProductsStage.setTitle("View Products");
        CheckProductTask(products);
        viewProductsStage.showAndWait();
    
    }
    
    
    
	private static void addStock(ArrayList<Product> products, UserInfo userInfo) {
		Stage addStockStage = new Stage();
		
		VBox vb = new VBox();
		vb.setPadding(new Insets(20));
		vb.setSpacing(5);
		
		
		
		Label addStockLabel = new Label("Add Stock");
		addStockLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		
		Label selectLabel = new Label("Enter Item Number to Add Stock");
		selectLabel.setStyle("-fx-font-size: 12;");
		TextField selectProduct = new TextField();
		
		Label QtyLabel = new Label("Enter quantity to add: ");
		QtyLabel.setStyle("-fx-font-size: 12;");
		TextField enterQty = new TextField();
		
		
		TextArea productList = new TextArea();
		productList.setEditable(false);
		productList.setPrefHeight(400);
		
		Label itemErrorLabel = new Label();
		Label qttErrorLabel = new Label();
		qttErrorLabel.setTextFill(Color.RED);
		
		Label sucLabel = new Label();
		sucLabel.setTextFill(Color.GREEN);
		
		for (Product product : products) {
			if (product instanceof Refrigerator) {
        		productList.appendText("Product Type: Refrigerator\n");
        	} else if (product instanceof TV) {
        		productList.appendText("Product Type: TV\n");
        	}else if (product instanceof Laptop) {
        		productList.appendText("Product Type: Laptop\n");
        	}
		    productList.appendText(product.toString() + "\n\n");  // Appends each product's information on a new line
		    productList.appendText("\n");
		}
		
		 Button addStockButton = new Button("Add Stock");
		 
		 addStockButton.setOnAction(event -> {
			  itemErrorLabel.setText("");
			  qttErrorLabel.setText("");
			  sucLabel.setText("");
			  
		        
	           String itemNum = selectProduct.getText();
	           try {
	        	    int quantity = Integer.parseInt(enterQty.getText());
	        	    boolean found = false;
	        	    
	        	    for (Product product : products) {
	        	    	if (product.getItemNumber().equals(itemNum)) {
	        	    		found = true;        	    		
	        	    	}
	        	    	
	        	    	if(found) {
	        	    		if(product.isActive()) {
		        	    		if (quantity <= 0) {
		        	    			qttErrorLabel.setText("Please enter a positive number.");        	    	
		        	    		}

		        	    		else if (quantity > 0 && product.getItemNumber().equals(itemNum)){
		        	    			//or (Product product : products) {		   		      	 	
		        	    			product.addQty(quantity);
		        	    			sucLabel.setText("Stock add completed.");
		        	    			addHistory(product,itemNum,quantity, "Add stock");
		        	    		} 		  
		        	    	}
		        	    	else {
		        	    		qttErrorLabel.setText("This product line is discontinued. Add stock failed." );
		    
		        	    	}
	        	    		break;	
	        	    	}

	        	    }
	        	    if (!found) {
	        	    	itemErrorLabel.setText("Item " + itemNum + " does not exist.");
	    	    		itemErrorLabel.setTextFill(Color.RED);
	    	    		sucLabel.setText("");
	    	    		selectProduct.clear();
	        	    }
	        	    
	   		       	   		       
	           }
	           catch (NumberFormatException e) {
	        	   qttErrorLabel.setText("Please enter a valid number");
	        	   
	           }
	           productList.clear();
			    for (Product product : products) {
			    	 if (product instanceof Refrigerator) {
			        		productList.appendText("Product Type: Refrigerator\n");
			        	} else if (product instanceof TV) {
			        		productList.appendText("Product Type: TV\n");
			        	}else if (product instanceof Laptop) {
			        		productList.appendText("Product Type: Laptop\n");
			        	}
			         productList.appendText(product.toString() + "\n");
			         productList.appendText("\n");
			    }			    
		    });
		
		Button backButton = new Button("Back");
	       backButton.setOnAction(event -> {
	       	addStockStage.close();
	           displayMenu(products, userInfo);
	       });
	       
	    HBox hb = new HBox();
	    hb.setPadding(new Insets(10));
	    hb.setSpacing(10);
	    hb.getChildren().addAll(addStockButton, backButton, sucLabel);
		
		
		vb.getChildren().addAll(addStockLabel, productList, selectLabel, selectProduct, itemErrorLabel,  QtyLabel, enterQty, qttErrorLabel, hb);
		
		Scene sc = new Scene(vb, 500, 440);
		
		addStockStage.setScene(sc);
		addStockStage.setTitle("Add Stock");
		addStockStage.show();
		
	}
	
	
	private static void deductStock(ArrayList<Product> products, UserInfo userInfo) {
		Stage addStockStage = new Stage();
		
		VBox vb = new VBox();
		vb.setPadding(new Insets(20));
		vb.setSpacing(5);
		
		
		
		Label deductStockLabel = new Label("Deduct Stock");
		deductStockLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		
		Label selectLabel = new Label("Enter Item Number to deduct Stock");
		selectLabel.setStyle("-fx-font-size: 12;");
		TextField selectProduct = new TextField();
		
		Label QtyLabel = new Label("Enter quantity to deduct: ");
		QtyLabel.setStyle("-fx-font-size: 12;");
		TextField enterQty = new TextField();
		
		
		TextArea productList = new TextArea();
		productList.setEditable(false);
		productList.setPrefHeight(400);
		
		Label itemErrorLabel = new Label();
		Label qttErrorLabel = new Label();
		qttErrorLabel.setTextFill(Color.RED);
		
		Label sucLabel = new Label();
		sucLabel.setTextFill(Color.GREEN);
		
		for (Product product : products) {
			if (product instanceof Refrigerator) {
        		productList.appendText("Product Type: Refrigerator\n");
        	} else if (product instanceof TV) {
        		productList.appendText("Product Type: TV\n");
        	}else if (product instanceof Laptop) {
        		productList.appendText("Product Type: Laptop\n");
        	}
		    productList.appendText(product.toString() + "\n");  // Appends each product's information on a new line
		    productList.appendText("\n");
		}
		

		 Button deductStockButton = new Button("Deduct Stock");
		 deductStockButton.setOnAction(event -> {
			   itemErrorLabel.setText("");
			   qttErrorLabel.setText("");
			   sucLabel.setText("");
		        
	           String itemNum = selectProduct.getText();
	          
	           try {
	        	    int quantity = Integer.parseInt(enterQty.getText());
	        	    boolean found = false;
	        	    
	        	    for (Product product : products) {
	        	    	
		        	    if (product.getItemNumber().equals(itemNum)) {
		        	    	found = true;		        	    	
		        	    }
		        	   
		        	    if (found) {
		        	    	if (product.getQuantity() == 0) {
		        	    		qttErrorLabel.setText("Quantity of item is 0. Failed to deduct");
		        	    		break;
		        	    	}
		        	    	if (product.isActive()) {
		        	    		if (quantity > 0) {

			        	    		if (quantity <= product.getQuantity()) {
			        	    			
				        	    		product.deductQty(quantity);
				        	    		sucLabel.setText("Stock deduct completed");
					   		      		CheckProductTask(products);
					   		      		addHistory(product, itemNum,quantity, "Deduct stock");
			        	    		}
				        			else
				        				qttErrorLabel.setText("Deduct stock failed. The current stock less than the deduct value.");		
			        	    	}
			        	    	else
			        	    		qttErrorLabel.setText("Please enter a positive number.");			
			        	    }
			        	    else {
			        	    	qttErrorLabel.setText("This product line is discontinued. Deduct stock failed.");
			        	    }
		        	    	break;
		        	    }

	   		       }
	        	   if(!found) {
   		           		itemErrorLabel.setText("Item " + itemNum + " does not exist.");
   		           		itemErrorLabel.setTextFill(Color.RED);
   		           		
   		           		selectProduct.clear();
	        	    }
	           }
	           catch (NumberFormatException e) {
	        	   qttErrorLabel.setText("Please enter a valid number");
	        	   
	           }
	           
	           productList.clear();
			    for (Product product : products) {
			    	 if (product instanceof Refrigerator) {
			        		productList.appendText("Product Type: Refrigerator\n");
			        	} else if (product instanceof TV) {
			        		productList.appendText("Product Type: TV\n");
			        	}else if (product instanceof Laptop) {
			        		productList.appendText("Product Type: Laptop\n");
			        	}
			         productList.appendText(product.toString() + "\n\n");
			         productList.appendText("\n");
			    }

		    });
		
		Button backButton = new Button("Back");
	       backButton.setOnAction(event -> {
	       	addStockStage.close();
	           displayMenu(products, userInfo);
	       });
	       
	    HBox hb = new HBox();
	    hb.setPadding(new Insets(10));
	    hb.setSpacing(10);
	    hb.getChildren().addAll(deductStockButton, backButton, sucLabel);
		
		
		vb.getChildren().addAll(deductStockLabel, productList, selectLabel, selectProduct, itemErrorLabel,  QtyLabel, enterQty, qttErrorLabel, hb);
		
		Scene sc = new Scene(vb, 400, 430);
		
		addStockStage.setScene(sc);
		addStockStage.setTitle("Deduct Stock");
		addStockStage.show();
		
	}
	
	
    private static void addRefrigerator(int maxProducts, Stage productTypeStage, ArrayList<Product> products) {
    	
        Stage stage = new Stage();
        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setPadding(new Insets(10));

        Label titleLabel = new Label("Add Refrigerator");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Name: (Exp: SHARP)");
        TextField nameField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label doorDesignLabel = new Label("Door Design:");
     //   TextField doorDesignField = new TextField();
        ComboBox<String> doorDesignField = new ComboBox<String>();
        doorDesignField.getItems().addAll("top freezer","bottom freezer","French door","side-by-side","underconuter","quad door");
        doorDesignField.setStyle("-fx-color: White");
        doorDesignField.setValue("top freezer");
        
        Label colorLabel = new Label("Color:");
      //  TextField colorField = new TextField();
        ComboBox<String> colorField = new ComboBox<String>();
        colorField.getItems().addAll("Stainless Steel","Black","White","Stale Gray","Red","Navy Blue","Red","Cream","Silver","Champagne Gold");
        colorField.setStyle("-fx-color: White");
        colorField.setValue("White");
        
        Label capacityLabel = new Label("Capacity (in Litres):");
        TextField capacityField = new TextField();
        
        Label itemNumberLabel = new Label("ItemNumber");
        TextField itemNumberField = new TextField();
        
        Label nameError = new Label();
        nameError.setTextFill(Color.RED);
        Label doorError = new Label();
        doorError.setTextFill(Color.RED);
        Label qttError = new Label();
        qttError.setTextFill(Color.RED);
        Label colorError = new Label();
        colorError.setTextFill(Color.RED);
        Label prcError = new Label();
        prcError.setTextFill(Color.RED);
        Label capError = new Label();
        capError.setTextFill(Color.RED);
        Label itemNumberError = new Label();
        itemNumberError.setTextFill(Color.RED);
        

        Button addButton = new Button("Add Refrigerator");
        addButton.setOnAction(event -> {
        	    String name = nameField.getText();
        	    String quantityText = quantityField.getText();
        	    String priceText = priceField.getText();
        	    String doorDesign = doorDesignField.getValue();
        	    String color = colorField.getValue();
        	    String capacityText = capacityField.getText();
        	    String itemNumber = itemNumberField.getText();
        	    
        	    nameError.setText("");
    	    	doorError.setText("");
    	    	qttError.setText("");
    	    	colorError.setText("");
    	        prcError.setText("");
    	    	capError.setText("");
    	    	itemNumberError.setText("");
    	    	
    	    	if(checkItemNumberExist(products,itemNumber)) {
    	    		
    	    		itemNumberError.setText("Duplicated itemNumber!");
    	    		clearFields(itemNumberField);
    	    		return;
    	    	}
    	    	
    	    	String[] fields = {name, quantityText,priceText,doorDesign,color,capacityText,itemNumber};
    	    	Label[] errorLabels = {
    	    			nameError,
    	    			qttError,
    	    			prcError,
    	    			doorError,
    	    			colorError,
    	    			capError,
    	    			itemNumberError};
    	    	
    	    	boolean empty = false;
    	    	for (int i = 0; i < fields.length; i++) {
    	    		System.out.println("Enter index "+i);
    	    		if (fields[i].isEmpty()) {
    	    			System.out.println("It is empty!");
    	    			errorLabels[i].setText("Cannot be empty!");
    	    			empty = true;
    	    		}
    	    	}
    	    	if (empty)
    	    		return;
        	    
	        	try {
	        		
		            
		            int quantity = Integer.parseInt(quantityField.getText());
		            double price = Double.parseDouble(priceField.getText());
		            boolean isActive = true;	            
		            int capacity = Integer.parseInt(capacityField.getText());
		            
		            if (quantity <= 0 || price <= 0 || capacity <= 0) {
		            	qttError.setText("Please enter a positive number!");
		        		prcError.setText("Please enter a positive number!");
		        		capError.setText("Please enter a positive number!");
		        		clearFields(quantityField, priceField, capacityField);
		        		return;
		            }
		
		            //String itemNumber = "R" + (products.size() + 1);
		            Refrigerator refrigerator = new Refrigerator(itemNumber, name, quantity, price, doorDesign, isActive,color, capacity);
		            products.add(refrigerator);
		            
		            productTypeStage.close(); // Close the "Select Product Type" stage
		            clearFields(nameField, quantityField, priceField, capacityField);
		            stage.close();
		            
		            
		            if (products.size() >= maxProducts) {
		                stage.close();
		                // Proceed to the next step or display a message
		            } else {
		                clearFields(nameField, quantityField, priceField, capacityField);
		            }
	        	}
	        	catch(NumberFormatException e) {
	        		
	        		// Check if the quantity is not a valid number
	        	    try {
	        	        Integer.parseInt(quantityField.getText());
	        	    } catch (NumberFormatException ex) {
	        	        qttError.setText("Please enter a valid number!");
	        	    }

	        	    // Check if the price is not a valid number
	        	    try {
	        	        Double.parseDouble(priceField.getText());
	        	    } catch (NumberFormatException ex) {
	        	        prcError.setText("Please enter a valid number!");
	        	    }

	        	    // Check if the capacity is not a valid number
	        	    try {
	        	        Integer.parseInt(capacityField.getText());
	        	    } catch (NumberFormatException ex) {
	        	        capError.setText("Please enter a valid number!");
	        	    }
	        	    try {
	        	    	Integer.parseInt(itemNumberField.getText());
	        	    } catch (NumberFormatException ex) {
	        	    	itemNumberError.setText("Please enter a valid number!");
	        	    }
	        	}
        });

        vb.getChildren().addAll(titleLabel, nameLabel, nameField, nameError, quantityLabel, quantityField, qttError, 
        		priceLabel, priceField, prcError, doorDesignLabel, doorDesignField, doorError, colorLabel, colorField, colorError, capacityLabel, capacityField, capError,itemNumberLabel, itemNumberField,itemNumberError,addButton);

        Scene scene = new Scene(vb, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Add Refrigerator");
        stage.show();
    }
    

    private static void addTV(int maxProducts, Stage productTypeStage, ArrayList<Product> products) {
    	
    
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));

        Label titleLabel = new Label("Add TV");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Name: (Exp: SAMSUNG G8)");
        TextField nameField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label screenTypeLabel = new Label("Screen Type:");
       // TextField screenTypeField = new TextField();
        ComboBox<String> screenTypeField = new ComboBox<String>();
        screenTypeField.getItems().addAll("LED", "OLED", "QLED", "MicroLED", "LCD", "Mini-LED", "Plasma", "ELD");
        screenTypeField.setStyle("-fx-color: White");
        screenTypeField.setValue("LED");
        
        Label resolutionLabel = new Label("Resolution:");
  //      TextField resolutionField = new TextField();
        ComboBox<String> resolutionField = new ComboBox<String>();
        resolutionField.getItems().addAll("Full HD (1080p) - 1920 x 1080", "Ultra HD (4K) - 3840 x 2160", "Ultra HD (4K) with HDR "
        		, "8K Ultra HD - 7680 x 4320", "HD Ready (720p) - 1280 x 720 ", "Quad HD (1440p) - 2560 x 1440", 
        		"5K Ultra HD - 5120 x 2880", "Full Array 8K - 7680 x 4320");
        resolutionField.setStyle("-fx-color: White");
        resolutionField.setValue("Ultra HD (4K) - 3840 x 2160");
        
        Label displaySizeLabel = new Label("Display Size (inch):");
        TextField displaySizeField = new TextField();
        
        Label itemNumberLabel = new Label("ItemNumber");
        TextField itemNumberField = new TextField();
        
        
        Label nameError = new Label();
        nameError.setTextFill(Color.RED);
        Label screenError = new Label();
        screenError.setTextFill(Color.RED);
        Label qttError = new Label();
        qttError.setTextFill(Color.RED);
        Label displayError = new Label();
        displayError.setTextFill(Color.RED);
        Label prcError = new Label();
        prcError.setTextFill(Color.RED);
        Label resError = new Label();
        resError.setTextFill(Color.RED);
        Label itemNumberError = new Label();
        itemNumberError.setTextFill(Color.RED);

        Button addButton = new Button("Add TV");
        addButton.setOnAction(event -> {
        	
        	String name = nameField.getText();
     	    String quantityText = quantityField.getText();
     	    String priceText = priceField.getText();
     	    String display = displaySizeField.getText();
     	    String res = resolutionField.getValue();
     	    String screenType = screenTypeField.getValue();
     	    String itemNumber = itemNumberField.getText();
     	    
     	    nameError.setText("");
     	    resError.setText("");
 	    	qttError.setText("");
 	    	displayError.setText("");
 	        prcError.setText("");
 	        screenError.setText("");
 	        itemNumberError.setText("");
 	        
 	       if(checkItemNumberExist(products,itemNumber)) {
	    		
	    		itemNumberError.setText("Duplicated itemNumber!");
	    		clearFields(itemNumberField);
	    		return;
	    	}
 	       
 	      String[] fields = {name, quantityText,priceText,display,res,screenType,itemNumber};
	    	Label[] errorLabels = {
	    			nameError,
	    			qttError,
	    			prcError,
	    			displayError,
	    			resError,
	    			screenError,
	    			itemNumberError};
 	       
 	      boolean empty = false;
	    	for (int i = 0; i < fields.length; i++) {
	    		System.out.println("Enter index "+i);
	    		if (fields[i].isEmpty()) {
	    			System.out.println("It is empty!");
	    			errorLabels[i].setText("Cannot be empty!");
	    			empty = true;
	    		}
	    	}
	    	if (empty)
	    		return;
 	       
 	       
        	
 	        try { 	         	        	
	            int quantity = Integer.parseInt(quantityField.getText());
	            double price = Double.parseDouble(priceField.getText());
	            boolean isActive = true;
	            double displaySize = Double.parseDouble(displaySizeField.getText());
	
	            //String itemNumber = "T" + (products.size() + 1);
	            TV tv = new TV(itemNumber, name, quantity, price, isActive, screenType, res, displaySize);
	            products.add(tv);
	            
	            productTypeStage.close();
	            clearFields(nameField, quantityField, priceField, displaySizeField);
	            stage.close();       
 	        }
 	       catch(NumberFormatException e) {
       		
       	    try {
       	        Integer.parseInt(quantityField.getText());
       	    } catch (NumberFormatException ex) {
       	        qttError.setText("Please enter a valid number!");
       	    }

       	    try {
       	        Double.parseDouble(priceField.getText());
       	    } catch (NumberFormatException ex) {
       	        prcError.setText("Please enter a valid number!");
       	    }
  
       	    try {
       	        Integer.parseInt(displaySizeField.getText());
       	    } catch (NumberFormatException ex) {
       	        displayError.setText("Please enter a valid number!");
       	    }
       	    try {
       	    	Integer.parseInt(itemNumberField.getText());
       	    } catch (NumberFormatException ex) {
       	    	itemNumberError.setText("Please enter a valid number!");
       	    }
       	}
            
        });

        root.getChildren().addAll(titleLabel, nameLabel, nameField, nameError, quantityLabel, quantityField, qttError, priceLabel, 
        		priceField, prcError, screenTypeLabel, screenTypeField, screenError, resolutionLabel, resolutionField, resError, displaySizeLabel, displaySizeField, displayError,itemNumberLabel, itemNumberField,itemNumberError,addButton);

        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Add TV");
        stage.show();
    }
   
    private static void addLaptop(int maxProducts, Stage productTypeStage, ArrayList<Product> products) {
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));

        Label titleLabel = new Label("Add Laptop");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Name: (e.g., HP Pavilion)");
        TextField nameField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label screenSizeLabel = new Label("Screen Size (inches):");
        TextField screenSizeField = new TextField();

        Label processorLabel = new Label("Processor: ");
  //      TextField processorField = new TextField();
        ComboBox<String> processorField = new ComboBox<String>();
        processorField.getItems().addAll("Intel Core i3", "Intel Core i5", "Intel Core i7", "Intel Core i9", 
        		"AMD Ryzen 3", "AMD Ryzen 5", "AMD Ryzen 7", "AMD Ryzen 9");
        processorField.setStyle("-fx-color: White");
        processorField.setValue("AMD Ryzen 3");
        
        
        Label ramLabel = new Label("RAM (GB):");
      //  TextField ramField = new TextField();
        ComboBox<String> ramField = new ComboBox<String>();
        ramField.getItems().addAll("4", "8", "16", "32", "64", "128");
        ramField.setStyle("-fx-color: White");
        ramField.setValue("4");
        
        Label storageLabel = new Label("Storage: ");
   //     TextField storageField = new TextField();
        ComboBox<String> storageField = new ComboBox<String>();
        storageField.getItems().addAll("128GB SSD", "256GB SSD", "512GB SSD", "1TB SSD", "2TB SSD", "500GB HDD", "1TB HDD", "2TB HDD");
        storageField.setStyle("-fx-color: White");
        storageField.setValue("128GB SSD");
        
        Label itemNumberLabel = new Label("ItemNumber");
        TextField itemNumberField = new TextField();
        

        Label nameLabelError = new Label();
        nameLabelError.setTextFill(Color.RED);
        Label quantityLabelError = new Label();
        quantityLabelError.setTextFill(Color.RED);
        Label priceLabelError = new Label();
        priceLabelError.setTextFill(Color.RED);
        Label screenSizeLabelError = new Label();
        screenSizeLabelError.setTextFill(Color.RED);
        Label processorLabelError = new Label();
        processorLabelError.setTextFill(Color.RED);
        Label ramLabelError = new Label();
        ramLabelError.setTextFill(Color.RED);
        Label storageLabelError = new Label();
        storageLabelError.setTextFill(Color.RED);
        Label itemNumberError = new Label();
        itemNumberError.setTextFill(Color.RED);


        Button addButton = new Button("Add Laptop");
        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String quantityText = quantityField.getText();
            String priceText = priceField.getText();
            String screenSizeText = screenSizeField.getText();
            String processor = processorField.getValue();
            String ramText = ramField.getValue();
            String storageText = storageField.getValue();
            String itemNumber = itemNumberField.getText();

            nameLabelError.setText("");
            quantityLabelError.setText("");
            priceLabelError.setText("");
            screenSizeLabelError.setText("");
            processorLabelError.setText("");
            ramLabelError.setText("");
            storageLabelError.setText("");
            itemNumberError.setText("");
            
            if(checkItemNumberExist(products,itemNumber)) {
	    		
	    		itemNumberError.setText("Duplicated itemNumber!");
	    		clearFields(itemNumberField);
	    		return;
	    	}

            String[] fields = {name, quantityText,priceText,screenSizeText,processor,ramText,storageText,itemNumber};
	    	Label[] errorLabels = {
	    			nameLabelError,
	    			quantityLabelError,
	    			priceLabelError,
	    			screenSizeLabelError,
	    			processorLabelError,
	    			ramLabelError,
	    			storageLabelError,
	    			itemNumberError};
 	       
 	      boolean empty = false;
	    	for (int i = 0; i < fields.length; i++) {
	    		System.out.println("Enter index "+i);
	    		if (fields[i].isEmpty()) {
	    			System.out.println("It is empty!");
	    			errorLabels[i].setText("Cannot be empty!");
	    			empty = true;
	    		}
	    	}
	    	if (empty)
	    		return;
            
            try {

                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);
                double screenSize = Double.parseDouble(screenSizeText);
                boolean isActive = true;

                //String itemNumber = "L" + (products.size() + 1);
                Laptop laptop = new Laptop(itemNumber, name, quantity, price, isActive, screenSize, processor, ramText, storageText);
                products.add(laptop);

                productTypeStage.close();
                clearFields(nameField, quantityField, priceField, screenSizeField);
                stage.close();
            }  catch(NumberFormatException e) {
           		
           	    try {
           	        Integer.parseInt(quantityField.getText());
           	    } catch (NumberFormatException ex) {
           	        quantityLabelError.setText("Please enter a valid number!");
           	    }

           	    try {
           	        Double.parseDouble(priceField.getText());
           	    } catch (NumberFormatException ex) {
           	        priceLabelError.setText("Please enter a valid number!");
           	    }
      
           	    try {
           	        Integer.parseInt(screenSizeField.getText());
           	    } catch (NumberFormatException ex) {
           	        screenSizeLabelError.setText("Please enter a valid number!");
           	    }
           	    try {
           	    	Integer.parseInt(itemNumberField.getText());
           	    } catch (NumberFormatException ex) {
           	    	itemNumberError.setText("Please enter a valid number!");
           	    }
           	}
        });

        root.getChildren().addAll(titleLabel, nameLabel, nameField, nameLabelError, quantityLabel, quantityField, quantityLabelError,
                priceLabel, priceField, priceLabelError, screenSizeLabel, screenSizeField, screenSizeLabelError,
                processorLabel, processorField, processorLabelError, ramLabel, ramField, ramLabelError,
                storageLabel, storageField, storageLabelError,itemNumberLabel, itemNumberField,itemNumberError, addButton);

        Scene scene = new Scene(root, 400,700);
        stage.setScene(scene);
        stage.setTitle("Add Laptop");
        stage.show();
    }
 
    
	private static void setStatus(ArrayList<Product> products, UserInfo userInfo) {
		
		Label stLabel = new Label("Set Status");
		stLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		 Stage stage = new Stage();
		 VBox vb = new VBox(10);
		 vb.setPadding(new Insets(10));

		 TextArea productList = new TextArea();
		 productList.setEditable(false);
		 productList.setPrefHeight(400);
		 
		 productList.clear();
		    for (Product product : products) {
		    	if (product instanceof Refrigerator) {
	        		productList.appendText("Product Type: Refrigerator\n");
	        	} else if (product instanceof TV) {
	        		productList.appendText("Product Type: TV\n");
	        	}else if (product instanceof Laptop) {
	        		productList.appendText("Product Type: Laptop\n");
	        	}
		         productList.appendText(product.toString() + "\n\n");
		    }
		    

	    // Label and TextField for entering the item number to discontinue
	     Label label = new Label("Enter the item number of the product to discontinue:");
         TextField itemNumberField = new TextField();
 	    
         Text success = new Text();
         		    
	     Button discontinueButton = new Button("Discontinue");
	     Button activeButton = new Button("Activate");
	     
	     discontinueButton.setOnAction(e -> {
	         String itemNumber = itemNumberField.getText();
	         boolean found = false;
	         for (Product product : products) {
	        	 if (product.getItemNumber().equals(itemNumber)) {
	        		 found = true;
			            	
	        		 if (!product.isActive()) {
			    		success.setText("Product is already discontinued");
		           		success.setFill(Color.BLUE);		           					           
	        		 }			       
	        		 else {
	        			 product.setActive(false);
				         success.setText("Product with item number " + itemNumber + " has been discontinued.\n");
				         success.setFill(Color.GREEN);					        	
				         addHistory(product,itemNumber,0,"Discontinue");
			       	}
	        		 break;
	            }
	        	 if (!found) {
			      	success.setText("Product with item number " + itemNumber + " not found.\n");
		        	success.setFill(Color.RED);		        	
			     }
	       }

	       //Clear the TextArea and repopulate with updated product detail
	       productList.clear();
	       for (Product product : products) {
	    	   if (product instanceof Refrigerator) {
	        		productList.appendText("Product Type: Refrigerator\n");
	        	} else if (product instanceof TV) {
	        		productList.appendText("Product Type: TV\n");
	        	}else if (product instanceof Laptop) {
	        		productList.appendText("Product Type: Laptop\n");
	        	}
	      	 productList.appendText(product.toString() + "\n\n");
	       }
	       
	      // Clear the itemNumberField after processing
          itemNumberField.clear();
		});
		    
		    
		    
	     activeButton.setOnAction(e -> {
	    	 String itemNumber = itemNumberField.getText();
		     boolean found = false;
	 
		     for (Product product : products) {
		     		    	 		         
		    	 if (product.getItemNumber().equals(itemNumber)) {
		    		 found = true;		            	
		            			            	
		    		 if (product.isActive()) {
		            		success.setText("Product is already active");
		            		success.setFill(Color.BLUE);		            			            	
		    		 }		            	
		    		 else {			                
		    			 product.setActive(true);			                
		    			 success.setText("Product with item number " + itemNumber + " has been activated.\n");				        	
		    			 success.setFill(Color.GREEN);	
		    			 addHistory(product,itemNumber,0,"Activate");
		    			 
		            }
		    		 break;
		    	 }
		         if (!found) {
		            success.setText("Product with item number " + itemNumber + " not found.\n");
			        success.setFill(Color.RED);			        	
		         }
		           
		    }
			    //Clear the TextArea and repopulate with updated product details
			    productList.clear();
			    for (Product product : products) {
			         productList.appendText(product.toString() + "\n");
			    }
			    
			    itemNumberField.clear();
			    
		    });
	     
		    
		    Button backButton = new Button("Back");
		       backButton.setOnAction(event -> {
		    	   stage.close();
		    	   productList.clear();
		           displayMenu(products, userInfo);
		       });
		       
		    HBox hb = new HBox();
		    hb.getChildren().addAll(activeButton, discontinueButton, backButton, success);
		    hb.setSpacing(5);
		    hb.setPadding(new Insets(10));

		    vb.getChildren().addAll(stLabel, productList, label, itemNumberField, hb);

		    Scene sc = new Scene(vb, 600, 600);
		    stage.setScene(sc);
		    stage.setTitle("Discontinue Product");
		    stage.show();	
		
	}	
	
	
	private static void displayInfo(UserInfo userInfo) {
		
		VBox vb = new VBox();
		vb.setPadding(new Insets(10));
		vb.setSpacing(40);
		
		Text text = new Text(50, 50, "User ID : " + userInfo.getUserID() + "\nUser Name: " + userInfo.getName() + "\n\n\n\nUser Action History");
		text.setFont(Font.font("Courier", FontWeight.BOLD, 20));
		
		Text text1 = new Text(50,50,"No action!");
		
		Button exit = new Button("Exit");
		exit.setPrefHeight(50);
		exit.setPrefWidth(50);
		exit.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15));
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setGridLinesVisible(true);

		
		pane.add(new Label("Timestamp"), 0, 0);
		pane.add(new Label("Product Type"), 1, 0);
		pane.add(new Label("ItemNumber"), 2, 0);
		pane.add(new Label("Quantity"), 3, 0);
		pane.add(new Label("Operation"), 4, 0);
		
		for(int i=0; i<history.length; i++) {
			if(history[i] != null) {
			addToGridPane(history[i], pane, i+1);
			}
			else 
				break;
		}
		
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		if (history[0] != null)
		{
		
			vb.getChildren().addAll(text, pane,exit);
		}
		else
			vb.getChildren().addAll(text, text1,exit);
		Scene sc = new Scene(vb, 800, 500);
		Stage stage = new Stage();
		stage.setScene(sc);
		stage.setTitle("Exit");
		stage.show();
		
	}
	
    
    private static void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    } 
    
    
    public static void showAlert(Product product, String productName, String ItemNumber) {
    	//Check if product is active; if not, return without showing the alert
    	if(!product.isActive()) {
    		return;
    	}
    	   	
    	Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Low Product Quantity");
            alert.setHeaderText(null);
            if (product instanceof Refrigerator) {
            	alert.setContentText("The quantity of R" + ItemNumber +"(Regrigerator) " + productName + " is low. Please replenish.");
            }
            else if (product instanceof TV) {
            	alert.setContentText("The quantity of T" + ItemNumber + "(TV)" + productName + " is low. Please replenish.");
            }
            else if (product instanceof Laptop) {
            	alert.setContentText("The quantity of L" + ItemNumber + "(Laptop)" + productName + " is low. Please replenish.");
            }

            setAlertPosition(alert);
            // Show the alert and wait for it to be closed
            alert.show();
        });
    }
    
    public static void setAlertPosition(Alert alert) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();
        double alertWidth = alert.getWidth();
        double alertHeight = alert.getHeight();

        // Set stage position
        double x = primaryScreenBounds.getMinX() + 20; // Adjust as needed
        double y = primaryScreenBounds.getMaxY() - alertHeight - 20; // Adjust as needed
        alert.setX(x);
        alert.setY(y);
    }
    
    public static void CheckProductTask (ArrayList<Product> products){
        
            for (Product product : products) {
                if (product.getQuantity() < NOTIFICATION_THRESHOLD) {
                    showAlert(product,product.getItemName(),product.getItemNumber()); // Show the alert if quantity is below threshold
                }
            }
        }
    
    public static void addHistory(Product product, String itemNumber, int quantity, String operation) {
    	if (product instanceof Refrigerator) {
    		history[historyIndex++] = new History(LocalDateTime.now(),"Refrigerator",itemNumber, quantity, operation);
    	}
    	else if (product instanceof TV) {
    		history[historyIndex++] = new History(LocalDateTime.now(),"TV",itemNumber, quantity, operation);
    	}
    	else if (product instanceof Laptop) {
    		history[historyIndex++] = new History(LocalDateTime.now(),"Laptop",itemNumber, quantity, operation);
    	}
    }
    
    public static void addToGridPane(History history, GridPane pane, int rowIndex) {
        pane.add(new Label(history.getTime().toString()), 0, rowIndex);
        pane.add(new Label(history.getType()), 1, rowIndex);
        pane.add(new Label(history.getItemNumber()), 2, rowIndex);
        pane.add(new Label(Integer.toString(history.getQuantity())), 3, rowIndex);
        pane.add(new Label(history.getOperation()), 4, rowIndex);
    }
    
    public static boolean checkItemNumberExist(ArrayList<Product> products, String itemNum) {
    	for (Product p: products) {

    		if(itemNum.equals(p.getItemNumber())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}


