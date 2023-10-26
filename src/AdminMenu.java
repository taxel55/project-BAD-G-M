import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class AdminMenu {
	Stage stage;
	BorderPane border;
	MenuBar menuBar;
	Menu menu;
	MenuItem manageProduct;
	MenuItem manageUser;
	Menu menuAccount;
	MenuItem logoff;
	TableView<Product>table;
	TableView<Customer>usertbl;
	Label nameLbl , priceLbl , stockLbl , typeLbl , usernamelbl , userphonelbl , useraddresslbl , useremaillbl , userpasslbl , usergenderlbl , userrolelbl, userdoblbl;
	TextField name , price , usernameTxt ,userphoneTxt , useraddressTxt , useremailTxt , userpasswordTxt;
	RadioButton male , female , user , admin;
	DatePicker dob;
	Spinner stock;
	ComboBox<String> type;
	GridPane grid , grid2 , griduser;
	Button insert , update ,delete , insert2 , update2 ,delete2;
	FlowPane button , gender ,role , button2;
	Connection con = Connection.getInstance();
	Vector<Product> produk;
	Vector<Customer> userList;
	ToggleGroup gendertgl , roletgl;
	
	
	public AdminMenu() {
		// TODO Auto-generated constructor stub
			init();
			position();
			addDataUser();
			updateUser();
			deleteUser();
			addProduct();
			updateProduct();
			deleteProduct();
			intoComboBox();
		  	menu.getItems().addAll(manageProduct , manageUser);
	        menuBar.getMenus().addAll(menu , menuAccount);
	        border.setTop(menuBar);
	        
	        manageProduct.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					managePro();
					
					BorderPane inPro = new BorderPane();
					inPro.setTop(table);
					inPro.setLeft(grid);
					inPro.setRight(grid2);
					inPro.setBottom(button);
					
					
					grid.setHgap(20);
					grid.setVgap(100);
					
					grid2.setHgap(20);
					grid2.setVgap(100);

					
					grid.setPadding(new Insets(75,0,0,100));
					grid2.setPadding(new Insets(75,100,0,0));
					border.setCenter(inPro);
					border.setBackground(null);
				}
			});
	        
	        manageUser.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					manageUser();
				
					BorderPane inUser = new BorderPane();
					inUser.setTop(usertbl);
					BorderPane inUser2 = new BorderPane();
					inUser.setCenter(inUser2);
					inUser.setLeft(griduser);
					inUser.setRight(button2);
					button2.setVgap(10);
					
					griduser.setPadding(new Insets(30,0,0,40));
					button2.setPadding(new Insets(100,60,0,0));
					griduser.setHgap(30);
					griduser.setVgap(35);
					border.setCenter(inUser);
					border.setBackground(null);
				}
			});
	        
	        logoff.setOnAction(new EventHandler<ActionEvent>() {
	        	Login login = new Login();
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					stage.close();
					try {
						login.start(stage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
	        
	        
	        
	        Scene scene = new Scene (border , 800 , 800);
	        Image img = new Image("https://i.pinimg.com/originals/e6/d8/71/e6d8714bfeb73722df91c928b9f333f0.jpg");
	        BackgroundImage bgimg = new BackgroundImage(img, 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
					new BackgroundSize(100,100,true,true ,true , true));
			Background bg = new Background(bgimg);
			border.setBackground(bg);
			stage.setResizable(false);
			stage.setTitle("Menu");
			
			Image logo = new Image("https://png.pngtree.com/template/20191019/ourlarge/pngtree-gm-letter-logo-vector-gm-initials-logo-designs-image_321122.jpg");
			
			
			stage.getIcons().add(logo);
			stage.setScene(scene);
			stage.show();
	}
	
	public void init() {
		stage = new Stage();
		border = new BorderPane();
		grid = new GridPane();
		grid2 = new GridPane();
		griduser = new GridPane();
		menuBar = new MenuBar();  
		menu = new Menu("Menu");  
		manageProduct = new MenuItem("Manage Product");
        manageUser = new MenuItem("Manage User");
        
		menuAccount = new Menu("Account");
        logoff = new MenuItem("Logoff");
        menuAccount.getItems().add(logoff);
        
        table = new TableView<Product>();
        nameLbl = new Label("Product Name");
        priceLbl = new Label("Product Price");
        stockLbl = new Label("Product Stock");
        typeLbl = new Label("Product Type");
        
        name = new TextField();
        price = new TextField();
        stock = new Spinner(1 , 100 , 0);
        type = new ComboBox<>();
        
    
        
        insert = new Button("Insert");
        update = new Button("Update");
        delete = new Button("Delete");
        button = new FlowPane();
        button2 = new FlowPane();
  
        produk = new Vector<>();
        
        //user
        gender = new FlowPane();
        role = new FlowPane();
        
        usertbl = new TableView<>();
        userList = new Vector<>();
        
        usernamelbl = new Label("User Name");
        userphonelbl = new Label("User Phone");
        useraddresslbl = new Label("User Address");
        useremaillbl = new Label("User Email");
        userpasslbl = new Label("User Password");
        usergenderlbl = new Label("User Gender");
        userrolelbl = new Label("User Role");
        userdoblbl = new Label("User Date of Birth");
        
        usernameTxt = new TextField();
        userphoneTxt = new TextField();
        useraddressTxt = new TextField();
        useremailTxt = new TextField();
        userpasswordTxt = new TextField();
        
        insert2 = new Button("Insert");
        update2 = new Button("Update");
        delete2 = new Button("Delete");
        
        dob = new DatePicker();
        
        male = new RadioButton("Male");
        female = new RadioButton("Female");
        user = new RadioButton("User");
        admin = new RadioButton("Admin");
        
        gendertgl = new ToggleGroup();
        roletgl = new ToggleGroup();
	}
	
	public void position() {
		button.getChildren().add(insert);
		button.getChildren().add(update);
		button.getChildren().add(delete);
		
		button2.getChildren().add(insert2);
		button2.getChildren().add(update2);
		button2.getChildren().add(delete2);
		
		button2.setOrientation(Orientation.VERTICAL);
		
		male.setToggleGroup(gendertgl);
		female.setToggleGroup(gendertgl);
		gender.getChildren().add(male);
		gender.getChildren().add(female);

		gender.setOrientation(Orientation.VERTICAL);
		
		user.setToggleGroup(roletgl);
		admin.setToggleGroup(roletgl);
		
		
		
		role.getChildren().add(user);
		role.getChildren().add(admin);
		
		role.setOrientation(Orientation.VERTICAL);
		
		
		grid.add(nameLbl, 0, 0);
		grid.add(name,1,0);
		grid2.add(priceLbl, 0, 0);
		grid2.add(price, 1, 0);
		grid.add(stockLbl, 0, 1);
		grid.add(stock, 1, 1);
		grid2.add(typeLbl, 0, 1);
		grid2.add(type, 1, 1);
		
		name.setPrefWidth(150);
		price.setPrefWidth(150);
		stock.setPrefWidth(150);
		type.setPrefWidth(150);
		
		button.setPadding(new Insets(0,0,30,250));
		button.setHgap(30);
		insert.setPrefSize(80, 40);
		update.setPrefSize(80, 40);
		delete.setPrefSize(80, 40);
		
		insert2.setPrefSize(80, 40);
		update2.setPrefSize(80, 40);
		delete2.setPrefSize(80, 40);
		
		griduser.add(usernamelbl, 0, 0);
		griduser.add(userphonelbl, 0, 1);
		griduser.add(useraddresslbl, 0, 2);
		griduser.add(useremaillbl, 0, 3);
		griduser.add(userpasslbl, 0, 4);
		griduser.add(usergenderlbl, 2, 0);
		griduser.add(userrolelbl, 2, 2);
		griduser.add(userdoblbl, 2, 4);
		
		griduser.add(usernameTxt, 1, 0);
		griduser.add(userphoneTxt, 1, 1);
		griduser.add(useraddressTxt, 1, 2);
		griduser.add(useremailTxt, 1, 3);
		griduser.add(userpasswordTxt, 1, 4);
		
		griduser.add(gender, 3, 0 , 1 ,6);
		griduser.add(role, 3, 2 ,1 , 10);
		griduser.add(dob, 3, 4);
		
	}
	
	//Manage Product
	
	public void managePro() {
		productTable();
	}
	
	public void productTable() {
		table.prefWidthProperty().bind(stage.widthProperty());
		table.setPrefHeight(350);
		
		TableColumn<Product, String > IdColumn = new TableColumn<Product, String>( "ID");
		IdColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("id"));
		IdColumn.setMinWidth(stage.getWidth()/5);
		
		TableColumn<Product, String > NameColumn = new TableColumn<Product, String>("Product Name");
		NameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));
		NameColumn.setMinWidth(stage.getWidth()/5);
		
		TableColumn<Product, String > TypeColumn = new TableColumn<Product, String>("Product Type");
		TypeColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("productType"));
		TypeColumn.setMinWidth(stage.getWidth()/5);
		
		TableColumn<Product, Integer > PriceColumn = new TableColumn<Product, Integer>("Price");
		PriceColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("price"));
		PriceColumn.setMinWidth(stage.getWidth()/5);
		
		TableColumn<Product, Integer >	StockColumn  = new TableColumn<Product, Integer>("Stock");
		StockColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("stock"));
		StockColumn.setMinWidth(stage.getWidth()/5);
		
		table.getColumns().addAll(IdColumn,NameColumn,TypeColumn,PriceColumn,StockColumn);
		refresh();
	}
	
	public void deleteProduct() {
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				Product selected = table.getSelectionModel().getSelectedItem();
				if (selected == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please choose product from table first");
					alert.showAndWait();
				}else {
					String id = selected.getId();
					String query = String.format("DELETE FROM ms_product WHERE id = '%s'",id);
					con.execUpdate(query);
					refresh();
				}
				
			}
		});
		
	}
	
	public void refresh() {	
		getData();
		ObservableList<Product> proObs = FXCollections.observableArrayList(produk);
		table.setItems(proObs);
	}
	
	
	
	public void getData() {
		produk.removeAllElements();
		String query  = "SELECT mp.id , mp.name , mpt.name , price , stock  FROM ms_product mp JOIN ms_product_type mpt ON mp.type_id = mpt.id ";
		con.res = con.execQuery(query);
		
		try {
			while(con.res.next()) {
				String id = con.res.getString("mp.id");
				String ProdName = con.res.getString("mp.name");
				String ProdType = con.res.getString("mpt.name");
				int price = con.res.getInt("price");
				int stock = con.res.getInt("stock");

				produk.add(new Product(id, ProdName, ProdType, price, stock));
			}
		} catch (Exception e) {
			
		}
	}
	
	public void intoComboBox() {
		String query = "SELECT name FROM ms_product_type";
		con.res=con.execQuery(query);
		
		try {
			while(con.res.next()) {
				type.getItems().addAll(con.res.getString("name"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void addProduct() {
		insert.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
					if (!(name.getText().length() > 5 || name.getText().length() < 30)) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Length");
						alert.showAndWait();
					}else if(price.getText().equals("0") || price.getText().length()== 0) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Price must be above 0");
						alert.showAndWait();
					}else if(!price.getText().matches("[0-9]*$")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Format");
						alert.showAndWait();
						
					
					}else {
						try {
							String count = "SELECT id FROM ms_product ORDER BY ID DESC LIMIT 1";
							con.res = con.execQuery(count);
							
							if (con.res.next()==false) {
								int ids =1;
								String id = String.format("PR" + "%03d" , ids);
								String names = name.getText();
								String prices = price.getText();

								int price = Integer.parseInt(prices);
								String stocks = stock.getEditor().getText();
								int stock = Integer.parseInt(stocks);
								
								if (type.getValue().equals("Shirts")) {
									String types = "TY001";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								}else if (type.getValue().equals("Dresses")) {
									String types = "TY002";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								}else if (type.getValue().equals("Jackets")) {
									String types = "TY003";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								}else if (type.getValue().equals("Trousers")) {
									String types = "TY004";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								}else if (type.getValue().equals("Jeans")) {
									String types = "TY005";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								}else if (type.getValue().equals("Shorts")) {
									String types = "TY006";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								}else if (type.getValue().equals("Skirts")) {
									String types = "TY007";
									String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
									con.execUpdate(query);
									refresh();
								
									
								}
							}else {
							do {
								
							
								String FN = con.res.getString("id");
								char first = FN.charAt(0);
								String fw = Character.toString(first);
								char second = FN.charAt(1);
								String sw = Character.toString(second);
								String FN1 = FN.replace(fw,"");
								String FNew = FN1.replace(sw,"");
	            
	            int ids = Integer.parseInt(FNew);
	            ids++;
	            String id = String.format("PR" + "%03d" , ids);
						String names = name.getText();
						String prices = price.getText();

						int price = Integer.parseInt(prices);
						String stocks = stock.getEditor().getText();
						int stock = Integer.parseInt(stocks);
						
						if (type.getValue().equals("Shirts")) {
							String types = "TY001";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Dresses")) {
							String types = "TY002";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Jackets")) {
							String types = "TY003";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Trousers")) {
							String types = "TY004";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Jeans")) {
							String types = "TY005";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Shorts")) {
							String types = "TY006";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Skirts")) {
							String types = "TY007";
							String query = String.format("INSERT INTO ms_product VALUES('%s','%s','%s','%d','%d')", id, types ,names,price,stock);
							con.execUpdate(query);
							refresh();
						
							
						}
							} while (con.res.next());
							}
							
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				
				}
		});
	}
	
	public void updateProduct() {
		update.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Product selected = table.getSelectionModel().getSelectedItem();
				if (selected == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please choose product from table first");
					alert.showAndWait();
				}else {
					if (!(name.getText().length() > 5 || name.getText().length() < 30)) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Length");
						alert.showAndWait();
					}else if(price.getText().equals("0") || price.getText().length()== 0) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Price must be above 0");
						alert.showAndWait();
					}else if(!price.getText().matches("[0-9]*$")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Format");
						alert.showAndWait();
					
					}else {
						String id = selected.getId();
						String names = name.getText();
						String prices = price.getText();

						int price = Integer.parseInt(prices);
						String stocks = stock.getEditor().getText();
						int stock = Integer.parseInt(stocks);
						
						if (type.getValue().equals("Shirts")) {
							String types = "TY001";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Dresses")) {
							String types = "TY002";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Jackets")) {
							String types = "TY003";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Trousers")) {
							String types = "TY004";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Jeans")) {
							String types = "TY005";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Shorts")) {
							String types = "TY006";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}else if (type.getValue().equals("Skirts")) {
							String types = "TY007";
							String query = String.format("UPDATE ms_product  SET name = '%s', "
									+ "type_id = '%s', price = '%d', stock = '%d' WHERE id = '%s' ", names, types , price, stock,id);
							con.execUpdate(query);
							refresh();
						}
						
						
						
					}
					
				
				}
			}
		});
	}
	

	
	//Manage Users
	
	public void manageUser() {
	userTable();
	}
	public void userTable() {
		usertbl.prefWidthProperty().bind(border.widthProperty());
		usertbl.prefHeight(500);
		
		TableColumn<Customer, String > IdColumn = new TableColumn<Customer, String>( "ID");
		IdColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("id"));
		IdColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String > NameColumn = new TableColumn<Customer, String>("UserName");
		NameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
		NameColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String > PhoneColumn = new TableColumn<Customer, String>("UserPhone");
		PhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
		PhoneColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String > AddressColumn = new TableColumn<Customer, String>("UserAddress");
		AddressColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));
		AddressColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String >	EmailColumn  = new TableColumn<Customer, String>("UserEmail");
		EmailColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
		EmailColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String >	PasswordColumn  = new TableColumn<Customer, String>("UserPassword");
		PasswordColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("password"));
		PasswordColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String >	GenderColumn  = new TableColumn<Customer, String>("UserGender");
		GenderColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("gender"));
		GenderColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String >	DOBColumn  = new TableColumn<Customer, String>("UserDOB");
		DOBColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("date"));
		DOBColumn.setMinWidth(stage.getWidth()/9);
		
		TableColumn<Customer, String >	RoleColumn  = new TableColumn<Customer, String>("UserRole");
		RoleColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("role"));
		RoleColumn.setMinWidth(stage.getWidth()/9);
		
		usertbl.getColumns().addAll(IdColumn, NameColumn, PhoneColumn, AddressColumn, EmailColumn, PasswordColumn, GenderColumn, DOBColumn , RoleColumn);
		refreshUser();
	}
	
	public void addDataUser() {
		insert2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!(usernameTxt.getText().length() > 5 || usernameTxt.getText().length() < 30)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Length");
					alert.showAndWait();
				}else if(!(userphoneTxt.getText().length()>10 || userphoneTxt.getText().length()<13 )) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Length");
					alert.showAndWait();
				}else if(!userphoneTxt.getText().matches("[0-9]*$")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
				
				}else if(!(useraddressTxt.getText().endsWith("Street"))){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
					
				}else if(!(useremailTxt.getText().matches("^(.+)@(.+)$*"))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
					
				}else if(!(userpasswordTxt.getText().length()>6 || userpasswordTxt.getText().length()<12 )) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Length");
					alert.showAndWait();
					
				}else if(!(userpasswordTxt.getText().matches("^[a-zA-Z0-9]+$"))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
				
				}else if(dob.getEditor().getText().length() == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Day");
					alert.showAndWait();
				}else if(!dob.getValue().isBefore(LocalDate.now())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Day");
					alert.showAndWait();
				
				}else if (gendertgl.getToggles().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Gender");
					alert.showAndWait();
				}else {
				// TODO Auto-generated method stub
					try {
						String count = "SELECT id FROM ms_customer ORDER BY ID DESC LIMIT 1";
						con.res = con.execQuery(count);
						
						
						
						
							
							if (con.res.next()==false) {
								int ids = 1;
					          
					            String id = String.format("US" + "%03d" , ids);
					            String name = usernameTxt.getText();
								String phone = userphoneTxt.getText();
								String address = useraddressTxt.getText();
								String email = useremailTxt.getText();
								String password =userpasswordTxt.getText();
								String gender;
								if (gendertgl.getSelectedToggle().equals(male)) {
									gender = "Male";
								} else {
									gender = "Female";
								}
								SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
								java.sql.Date dates = java.sql.Date.valueOf(dob.getValue());
								String date = fomat.format(dates);
								
								String role;
								if (roletgl.getSelectedToggle().equals(admin)) {
									role = "admin";
								} else {
									role = "user";
								}
								
								String query = String.format("INSERT INTO ms_customer VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')", id , name , phone, address , email , password , gender , date , role);
								con.execUpdate(query);
												refreshUser();
											}
							else {
							
				do {
					String FN = con.res.getString("id");
					char first = FN.charAt(0);
					String fw = Character.toString(first);
					char second = FN.charAt(1);
					String sw = Character.toString(second);
					String FN1 = FN.replace(fw,"");
					String FNew = FN1.replace(sw,"");
		            
					 int ids = Integer.parseInt(FNew);
			            ids++;
		            String id = String.format("US" + "%03d" , ids);
		            
					String name = usernameTxt.getText();
					String phone = userphoneTxt.getText();
					String address = useraddressTxt.getText();
					String email = useremailTxt.getText();
					String password =userpasswordTxt.getText();
					String gender;
					if (gendertgl.getSelectedToggle().equals(male)) {
						gender = "Male";
					} else {
						gender = "Female";
					}
					SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
					java.sql.Date dates = java.sql.Date.valueOf(dob.getValue());
					String date = fomat.format(dates);
					
					String role;
					if (roletgl.getSelectedToggle().equals(admin)) {
						role = "admin";
					} else {
						role = "user";
					}
					
					String query = String.format("INSERT INTO ms_customer VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')", id , name , phone, address , email , password , gender , date , role);
					con.execUpdate(query);
									refreshUser();
													
								
								
							
				} while (con.res.next());	
							}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
				}
				}
		});
		
	}
	
	public void deleteUser() {
		delete2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				Customer selected = usertbl.getSelectionModel().getSelectedItem();
				if (selected == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please choose product from table first");
					alert.showAndWait();
				}else {
					String id = selected.getId();
					String query = String.format("DELETE FROM ms_customer WHERE id = '%s'",id);
					con.execUpdate(query);
					refreshUser();
				}
				
			}
		});
		
	}
	
	public void updateUser() {
		update2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Customer selected = usertbl.getSelectionModel().getSelectedItem();
				if (selected == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Please choose product from table first");
					alert.showAndWait();
				}else {
					if (!(usernameTxt.getText().length() > 5 || usernameTxt.getText().length() < 30)) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Length");
						alert.showAndWait();
					}else if(!(userphoneTxt.getText().length()>10 || userphoneTxt.getText().length()<13 )) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Length");
						alert.showAndWait();
					}else if(!userphoneTxt.getText().matches("[0-9]*$")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Format");
						alert.showAndWait();
					
					}else if(!(useraddressTxt.getText().endsWith("Street"))){
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Format");
						alert.showAndWait();
						
					}else if(!(useremailTxt.getText().matches("^(.+)@(.+)$*"))) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Format");
						alert.showAndWait();
						
					}else if(!(userpasswordTxt.getText().length()>6 || userpasswordTxt.getText().length()<12 )) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Length");
						alert.showAndWait();
						
					}else if(!(userpasswordTxt.getText().matches("^[a-zA-Z0-9]+$"))) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Format");
						alert.showAndWait();
					
					}else if(dob.getEditor().getText().length() == 0) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Day");
						alert.showAndWait();
					}else if(!dob.getValue().isBefore(LocalDate.now())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Day");
						alert.showAndWait();
					
					}else if (gendertgl.getToggles().isEmpty()) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid Gender");
						alert.showAndWait();
					}else {
					// TODO Auto-generated method stub
						String id = selected.getId();
					String name = usernameTxt.getText();
					String phone = userphoneTxt.getText();
					String address = useraddressTxt.getText();
					String email = useremailTxt.getText();
					String password =userpasswordTxt.getText();
					
					String gender;
					if (gendertgl.getSelectedToggle().equals(male)) {
						gender = "Male";
					} else {
						gender = "Female";
					}
					SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
					java.sql.Date dates = java.sql.Date.valueOf(dob.getValue());
					String date = fomat.format(dates);
					
					String role;
					if (roletgl.getSelectedToggle().equals(admin)) {
						role = "admin";
					} else {
						role = "user";
					}
					
					String query = String.format("UPDATE ms_customer SET name = '%s' , phone = '%s', address = '%s', email = '%s' , password = '%s', gender = '%s'"
							+ ", dob = '%s', role = '%s' WHERE id = '%s'", name ,phone, address , email , password , gender , date , role,id );
					con.execUpdate(query);
					refreshUser();
				}
				
				}
			}
		});
		
	}
	public void refreshUser() {	
		getDataCust();
		ObservableList<Customer> Custobs = FXCollections.observableArrayList(userList);
		usertbl.setItems(Custobs);
	}
	
	public void getDataCust() {
		userList.removeAllElements();
		String query  = "SELECT * FROM ms_customer";
		con.res = con.execQuery(query);
		try {
			while(con.res.next()) {
			String id = con.res.getString("id");
			String username = con.res.getString("name");
			String userphone = con.res.getString("phone");
			String useraddress = con.res.getString("address");
			String useremail = con.res.getString("email");
			String userpassword = con.res.getString("password");
			String usergender = con.res.getString("gender");
			String userdate = con.res.getString("dob");
			String userrole = con.res.getString("role");
			userList.add(new Customer(id, username , userphone ,useraddress,useremail , userpassword, usergender , userdate , userrole ));
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				
	}
	
	
}
