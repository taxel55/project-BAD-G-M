import java.sql.SQLException;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserMenu {
	Stage stage;
	BorderPane border;
	MenuBar menuBar;
	Menu menu;
	MenuItem buy;
	MenuItem transaction;
	Menu menuAccount;
	MenuItem logoff;
	TableView<Product> table;
	TableView<Cart> table2;
	TableView<Transaction> transactiontbl;
	TableView<TransactionDetail> transprodtbl;
	
	Vector<Product> produk;
	Vector<Cart> cart;
	Vector<Transaction> translist;
	Vector<TransactionDetail> transprodlist;
	Connection con = Connection.getInstance();
	GridPane grid , grid2;
	Button add , remove, order;
	Spinner numspin;
	Label totalLbl;
	TextField total;
	
	 String iduser;
	
	
	public UserMenu(String id) {
		// TODO Auto-generated constructor stub
		this.iduser = id;
		
		init();
		position();
		addToCart();
		removeToCart();
		orderTran();
	 menu.getItems().addAll(buy , transaction);
        menuBar.getMenus().addAll(menu , menuAccount);
        border.setTop(menuBar);
        
        buy.setOnAction(new EventHandler<ActionEvent>() {
        	
        
        	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
				BorderPane inBuy = new BorderPane();
				buyMenu();
				buyMenuTable2();
				inBuy.setTop(table);
				inBuy.setLeft(table2);
				inBuy.setCenter(grid);
				
				
				
				
				grid.setVgap(10);
				grid.setPadding(new Insets(100,0,0,200));
				
				
				BorderPane.setMargin(table, new Insets(0));
				BorderPane.setMargin(table2, new Insets(0));
				BorderPane.setMargin(grid, new Insets(0));

				
				border.setCenter(inBuy);
				border.setBackground(null);
				
			}
        	
        });
        
        transaction.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				BorderPane inTrans = new BorderPane();
				BorderPane inTrans2 = new BorderPane();
				transactionMenu();transactionMenu2();
				inTrans.setLeft(transactiontbl);
				inTrans2.setTop(transprodtbl);
				inTrans2.setBottom(grid2);
				inTrans.setCenter(inTrans2);
			
				
				BorderPane.setAlignment(transprodtbl, Pos.TOP_LEFT);
				grid2.setPadding(new Insets(0,0,25,170));
				grid2.setHgap(30);
				grid.setVgap(50);
				border.setCenter(inTrans);
				border.setBackground(null);
			}
		 
		});
        
        Login login = new Login();
        
        logoff.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				stage.close();
				try {
					login.start(stage);
				} catch (Exception e) {
					// TODO: handle exception
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
		stage.setScene(scene);
		Image logo = new Image("https://png.pngtree.com/template/20191019/ourlarge/pngtree-gm-letter-logo-vector-gm-initials-logo-designs-image_321122.jpg");
		stage.getIcons().add(logo);
		stage.show();
	}
	
	public void init() {
		stage = new Stage();
		border = new BorderPane();
		grid = new GridPane();
		grid2 = new GridPane();
		
		add = new Button("Add to Cart");
		remove = new Button("Remove From Cart");
		order = new Button("Order");
		
		
		numspin = new Spinner(1,100,0);
		menuBar = new MenuBar();  
        menu = new Menu("Menu");  
        buy = new MenuItem("Buy");
        transaction = new MenuItem("Transaction");
        
        menuAccount = new Menu("Account");
        logoff = new MenuItem("Logoff");
        menuAccount.getItems().add(logoff);
        
        
        produk = new Vector<Product>();
        cart = new Vector<Cart>();
        translist = new Vector<Transaction>();
        table = new TableView<>();
        table2 = new TableView<>();
        
        transactiontbl = new TableView<Transaction>();
        transprodtbl = new TableView<TransactionDetail>();
        
        transprodlist = new Vector<TransactionDetail>();
        
        totalLbl = new Label("Total Price");
        total = new TextField();
        total.setEditable(false);
        
	}
	
	public void position() {
		grid.add(numspin, 0, 1);
		grid.add(add, 0, 2);
		grid.add(remove, 0, 3);
		grid.add(order, 0, 4);
		
		numspin.setPrefWidth(150);
		add.setPrefWidth(150);
		remove.setPrefWidth(150);
		order.setPrefWidth(150);
		
		
		
		grid2.add(totalLbl, 0, 0);
		grid2.add(total, 1, 0);
	}
	
	//Buy Menu
	
	public void buyMenu() {
		table.prefWidthProperty().bind(stage.widthProperty());
		table.setPrefHeight(400);
		
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
		PriceColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Price"));
		PriceColumn.setMinWidth(stage.getWidth()/5);
		
		TableColumn<Product, Integer >	StockColumn  = new TableColumn<Product, Integer>("Stock");
		StockColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Stock"));
		StockColumn.setMinWidth(stage.getWidth()/5);
		
		table.getColumns().addAll(IdColumn,NameColumn,TypeColumn,PriceColumn,StockColumn);
		
		
		
		refreshProduct();
		
		
	}
	
	public void buyMenuTable2() {
		table2.setMaxWidth(300);
		table2.setMaxHeight(400);
		
		table2.setMinWidth(300);
		table2.setMinHeight(400);
		
		TableColumn<Cart, String > IdColumn2 = new TableColumn<Cart, String>( "ID");
		IdColumn2.setCellValueFactory(new PropertyValueFactory<Cart,String>("productId"));
		IdColumn2.setMinWidth(300/2);
		
		TableColumn<Cart, Integer > QuantityColumn = new TableColumn<Cart, Integer>("Quantity");
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("quantity"));
		QuantityColumn.setMinWidth(300/2);
		
		
		
		table2.getColumns().addAll(IdColumn2, QuantityColumn);
		refreshCart();
	}
	
	public void refreshCart() {
		getDataCart();
		ObservableList<Cart> cart0bs = FXCollections.observableArrayList(cart);
		table2.setItems(cart0bs);
	}
	
	public void getDataCart() {
		
		cart.removeAllElements();
		String query  = String.format("SELECT mp.id  , mc.quantity FROM ms_product mp JOIN ms_cart mc ON mp.id = mc.product_id  WHERE mc.cust_id = '%s'", iduser);
		con.res = con.execQuery(query);
		try {
			while(con.res.next()) {
				
				
				String productId = con.res.getString("mp.id");
			
				int quantity = con.res.getInt("quantity");
				
				

				cart.add(new Cart(iduser , productId , quantity));
			}
		} catch (Exception e) {
		
		}
	}
	
	public void addToCart() {
		add.setOnAction(new EventHandler<ActionEvent>() {

			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Product selected =  table.getSelectionModel().getSelectedItem();
				String query2 ="SELECT * FROM ms_cart mc JOIN ms_product mp ON mc.product_id = mp.id";
				con.res = con.execQuery(query2);
				
				try {
					do {
						if (selected == null) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("Please choose product from table first");
							alert.showAndWait();
							
						}else if(con.res.next() == true) {
							
								
								if(con.res.getString("mc.product_id").equals(selected.getId())) {
									
									Alert alert = new Alert(AlertType.ERROR);
									alert.setContentText("P");
									alert.showAndWait();
									break;
								}else {
									String prodids = selected.getId();
									String Squantity = numspin.getEditor().getText();
									int quantity = Integer.parseInt(Squantity);
									
									
									String query = String.format("INSERT INTO ms_cart VALUES('%d', '%s', '%s','%d')" ,0 , iduser , prodids , quantity );
									con.execUpdate(query);
									refreshCart();
									break;
								}
							}else {
								String prodids = selected.getId();
								String Squantity = numspin.getEditor().getText();
								int quantity = Integer.parseInt(Squantity);
								
								
								String query = String.format("INSERT INTO ms_cart VALUES('%d', '%s', '%s','%d')" ,0 , iduser , prodids , quantity );
								con.execUpdate(query);
								refreshCart();
								break;
							}
					} while (con.res.next());
					
						
					
	
			

				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		
	}
	
	public void removeToCart() {
		remove.setOnAction(new EventHandler<ActionEvent>() {
			
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Cart selected =  table2.getSelectionModel().getSelectedItem();
				try {
					if (selected == null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Please choose product from table first");
						alert.showAndWait();
					}else {
						
						String id = selected.getProductId();
						String query = String.format("DELETE FROM ms_cart WHERE product_id = '%s' ", id);
						 con.execUpdate(query);
						 refreshCart();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
	
	public void orderTran() {
		order.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ObservableList<Cart> cart0bs = FXCollections.observableArrayList(cart);
				table2.setItems(cart0bs);
				String idt = "";
				String query03 = String.format("SELECT * FROM ms_cart mc JOIN ms_product mp ON mc.product_id = mp.id");
				con.res = con.execQuery(query03);
				if (cart0bs.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Cart is Empty");
					alert.showAndWait();
				} else
					
					try {
						while (con.res.next()) {
							
							
						
						if (con.res.getInt("mp.stock") < con.res.getInt("mc.quantity")) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("Quantity is too much");
							alert.showAndWait();
						}
						else {
								
								String Squantity = numspin.getEditor().getText();
								int quantity = Integer.parseInt(Squantity);
								
								String query0 = String.format("SELECT*FROM ms_product mp JOIN ms_cart mc ON mp.id = mc.product_id WHERE cust_id = '%s'", iduser);
								con.res = con.execQuery(query0);
								int finalq = 0;
								String cpid = "";
								try {
									while (con.res.next()) {
										finalq = con.res.getInt("stock") - quantity;
										 cpid = con.res.getString("mc.product_id");
										
									}
									String querys = String.format("UPDATE  ms_product JOIN ms_cart ON ms_product.id = ms_cart.product_id  SET stock = %d WHERE ms_cart.product_id = '%s'", finalq , cpid  );
									con.execUpdate(querys);
									refreshProduct();
									
									
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								
								
								
								
						
							try {
								String qry = "SELECT id FROM transaction_header ORDER BY ID DESC LIMIT 1";
								con.res = con.execQuery(qry);
								
								
									if (con.res.next()==false) {
										
										int num = 1;
										
										String id = String.format("TR" + "%03d", num);
										String query = String.format("INSERT INTO transaction_header VALUES ('%s' , '%s' , '%s')", id , iduser , java.time.LocalDate.now());
										con.execUpdate(query);
										
										query = "SELECT * from transaction_header";
										con.res = con.execQuery(query);
										
										try {
											while (con.res.next()) {
												
												
											}
										} catch (Exception e) {
											// TODO: handle exception
										}
										
										
										String queryd = String.format("SELECT th.id , mct.product_id , mp.name ,mp.price, mct.quantity FROM ms_product mp JOIN ms_cart mct ON mp.id = mct.product_id JOIN ms_customer mc ON mct.cust_id = mc.id JOIN transaction_header th ON mc.id = th.user_id WHERE cust_id = '%s'", iduser);
										con.res = con.execQuery(queryd);
										String proid ="";
										int proquan =0;
										try {
											while(con.res.next()) {
												 proid = con.res.getString("mct.product_id");
												 proquan = con.res.getInt("mct.quantity");
												idt = con.res.getString("th.id");
												
												
												
												
											} ;
											
											
											} catch (Exception e) {
											// TODO: handle exception
												
										}
										String query3 = String.format("INSERT INTO transaction_detail VALUES ('%s', '%s','%d' )", idt , proid , proquan );
										con.execUpdate(query3);
									
										String query2 = String.format("DELETE from ms_cart  where cust_id ='%s'", iduser);
										con.execUpdate(query2);
										refreshCart();
										
									}else  {
										
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
											String id = String.format("TR" + "%03d", ids);
											String query = String.format("INSERT INTO transaction_header VALUES ('%s' , '%s' , '%s')", id , iduser , java.time.LocalDate.now());
											con.execUpdate(query);
											
											String querys = "SELECT * from transaction_header";
											con.res = con.execQuery(querys);
											
											try {
												while (con.res.next()) {
													idt = con.res.getString("id");
													
												}
											} catch (Exception e) {
												// TODO: handle exception
											}
											
											
											String queryd = String.format("SELECT th.id , mct.product_id , mp.name ,mp.price, mct.quantity FROM ms_product mp JOIN ms_cart mct ON mp.id = mct.product_id JOIN ms_customer mc ON mct.cust_id = mc.id JOIN transaction_header th ON mc.id = th.user_id WHERE cust_id = '%s'", iduser);
											con.res = con.execQuery(queryd);
											String proid ="";
											int proquan =0;
											try {
												while(con.res.next()) {
													 proid = con.res.getString("mct.product_id");
													 proquan = con.res.getInt("mct.quantity");
													idt = con.res.getString("th.id");
													
													
												} ;
												
												
												} catch (Exception e) {
												// TODO: handle exception
											}
											
											String query2 = String.format("INSERT INTO transaction_detail VALUES ('%s', '%s','%d' )", idt , proid , proquan );
											con.execUpdate(query2);
											
											String query3 = String.format("DELETE from ms_cart  where cust_id ='%s'", iduser);
											con.execUpdate(query3);
											refreshCart();
									}while(con.res.next());
										
										
										
										
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}
				
		});
	}
	
	public void refreshProduct() {	
		getDataProduct();
		ObservableList<Product> proObs = FXCollections.observableArrayList(produk);
		table.setItems(proObs);
		
	}
	
	
	
	
	
	public void getDataProduct() {
		produk.removeAllElements();
		String query  = "SELECT mp.id , mp.name , mpt.name , price , stock  FROM ms_product mp JOIN ms_product_type mpt ON mp.type_id = mpt.id ";
		con.res = con.execQuery(query);
		
		try {
			while(con.res.next()) {
				String ID = con.res.getString("mp.id");
				String ProdName = con.res.getString("mp.name");
				String ProdType = con.res.getString("mpt.name");
				int price = con.res.getInt("price");
				int stock = con.res.getInt("stock");

				produk.add(new Product(ID, ProdName, ProdType, price, stock));
			}
		} catch (Exception e) {
			
		}
		
		
	}
	
	
	
	//Transaction menu
	
	public void transactionMenu() {
		transactiontbl.prefHeightProperty().bind(stage.heightProperty());
		
		transactiontbl.setPrefWidth(250);
		TableColumn<Transaction, String > idTransaction = new TableColumn<Transaction, String>( "ID");
		idTransaction.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transactionid"));
		idTransaction.setMinWidth(transactiontbl.getPrefWidth()/2);
		
		TableColumn<Transaction, String > DateColumn = new TableColumn<Transaction, String>("Transaction Date");
		DateColumn.setCellValueFactory(new PropertyValueFactory<Transaction,String>("transactiondate"));
		DateColumn.setMinWidth(transactiontbl.getPrefWidth()/2);
		
		transactiontbl.getColumns().addAll(idTransaction , DateColumn);
		
		
		
		refreshTransaction();
		
		}
	
	public void refreshTransaction() {
		getDataTransaction();
		ObservableList<Transaction> transobs = FXCollections.observableArrayList(translist);
		transactiontbl.setItems(transobs);
	}
	
	public void getDataTransaction() {
		translist.removeAllElements();
		//String query = String.format("SELECT * FROM ms_product_type mpt JOIN ms_product mp ON mpt.id = mp.type_id JOIN ms_cart mct ON mp.id = mct.product_id JOIN ms_customer mc ON mct.cust_id = mc.id JOIN transaction_header th ON mc.id = th.user_id WHERE th.user_id = '%s'",  iduser );
		String query = String.format("SELECT * FROM transaction_header WHERE user_id = '%s' ", iduser);
		con.res = con.execQuery(query);

		try {
			while(con.res.next()) {
				String transactionid = con.res.getString("id");
				String transactiondate = con.res.getString("tr_date");
//				String prodid = con.res.getString("mct.product_id");
//				String prodname = con.res.getString("mp.name");
//				String prodtp = con.res.getString("mpt.name");
//				int prquan = con.res.getInt("mct.quantity");
			translist.add(new Transaction(transactionid , iduser,  transactiondate));
			//translist.add(new Transaction(transactionid ,prodid,  iduser , prodname , prodtp , prquan, transactiondate));
					
					
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void transactionMenu2() {
		transprodtbl.setPrefWidth(550);
		transprodtbl.setMinHeight(700);
		transprodtbl.setMaxHeight(700);
		
		TableColumn<TransactionDetail, String > IdColumn = new TableColumn<TransactionDetail, String>( "Product ID");
		IdColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail,String>("productid"));
		IdColumn.setMinWidth(transprodtbl.getPrefWidth()/4);
		
		TableColumn<TransactionDetail, String > NameColumn = new TableColumn<TransactionDetail, String>("Product Name");
		NameColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail,String>("productname"));
		NameColumn.setMinWidth(transprodtbl.getPrefWidth()/4);
		
		TableColumn<TransactionDetail, String > TypeColumn = new TableColumn<TransactionDetail, String>("Product Type");
		TypeColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail,String>("producttype"));
		TypeColumn.setMinWidth(transprodtbl.getPrefWidth()/4);
		
		TableColumn<TransactionDetail, Integer > QuantityColumn = new TableColumn<TransactionDetail, Integer>("Quantity");
		QuantityColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail,Integer>("quantity"));
		QuantityColumn.setMinWidth(transprodtbl.getPrefWidth()/4);
		
		transprodtbl.getColumns().addAll(IdColumn , NameColumn , TypeColumn, QuantityColumn);
		refreshTransDetail();
	}
	
	public void refreshTransDetail() {
		getTransDetail();
		ObservableList<TransactionDetail> transobs = FXCollections.observableArrayList(transprodlist);
		transprodtbl.setItems(transobs);
		
	}
	
	public void getTransDetail() {
		
	
	

	transactiontbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			transprodlist.removeAllElements();
			int totalprice = 0;
			Transaction selected = transactiontbl.getSelectionModel().getSelectedItem();
			String idtt = selected.getTransactionid();
			
			
			String query = String.format("SELECT * FROM transaction_header th JOIN  transaction_detail td ON th.id = td.tr_id  JOIN ms_product mp ON td.product_id = mp.id JOIN ms_product_type mpt ON mp.type_id = mpt.id WHERE  th.id = '%s'  ",  idtt  );
			con.res = con.execQuery(query);
			
			try {
				while (con.res.next()) {
				String idtrans = con.res.getString("td.tr_id");
				String prodid = con.res.getString("td.product_id");
				String prodname = con.res.getString("mp.name");
				String prodtp = con.res.getString("mpt.name");
				int quan = con.res.getInt("td.quantity");
				int pric = con.res.getInt("mp.price");
				pric *= quan;
				totalprice += pric;
				
				
					transprodlist.add(new TransactionDetail(idtrans, prodid, prodname, prodtp, quan));
					refreshTransDetail();
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			total.setText(Integer.toString(totalprice));
		}
	});
		
			

				
			
			
		
		
		
		
	}
	
	
	
	
	

}
