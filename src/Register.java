

import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Register{
	Stage stage;
	
	
	BorderPane border;
	GridPane grid;
	FlowPane flow;
	FlowPane flowgender;
	Label register  ,useridLbl , usernameLbl , phoneLbl , addressLbl , emailLbl , passwordLbl , dobLbl , genderLbl;
	TextField userid , username , userphone , useraddress , useremail ;
	Button signIn2 , signUp2;
	PasswordField userpassword;
	DatePicker dob;
	RadioButton male , female;
	ToggleGroup gendertgl;
	Connection con = Connection.getInstance();
	Vector<Customer> userList = new Vector<>();
	public  Register() {
		
		
		init();
		position();
	
		Login login = new Login();
		signIn2.setOnAction(new EventHandler<ActionEvent>() {

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
		
		signUp2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if (!(username.getText().length() > 5 || username.getText().length() < 30)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Length");
					alert.showAndWait();
				}else if(!(userphone.getText().length()>10 || userphone.getText().length()<13 )) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Length");
					alert.showAndWait();
				}else if(!userphone.getText().matches("[0-9]*$")) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
				
				}else if(!(useraddress.getText().endsWith("Street"))){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
					
				}else if(!(useremail.getText().matches("^(.+)@(.+)$*"))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Format");
					alert.showAndWait();
					
				}else if(!(userpassword.getText().length()>6 || userpassword.getText().length()<12 )) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Invalid Length");
					alert.showAndWait();
					
				}else if(!(userpassword.getText().matches("^[a-zA-Z0-9]+$"))) {
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
				
					
					try {
						String count = "SELECT id FROM ms_customer ORDER BY ID DESC LIMIT 1";
						con.res = con.execQuery(count);
						
						if (con.res.next()==false) {
							int ids = 1;
							String id = String.format("US" + "%03d" , ids);
				            
							String name = username.getText();
							String phone = userphone.getText();
							String address = useraddress.getText();
							String email = useremail.getText();
							String password =userpassword.getText();
							String gender;
							if (gendertgl.getSelectedToggle().equals(male)) {
								gender = "Male";
							} else {
								gender = "Female";
							}
							SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
							java.sql.Date dates = java.sql.Date.valueOf(dob.getValue());
							String date = fomat.format(dates);
							
							String role = "user";
							
							String query = String.format("INSERT INTO ms_customer VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')", id , name , phone, address , email , password , gender , date , role);
							con.execUpdate(query);
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
            String id = String.format("US" + "%03d" , ids);
            
			String name = username.getText();
			String phone = userphone.getText();
			String address = useraddress.getText();
			String email = useremail.getText();
			String password =userpassword.getText();
			String gender;
			if (gendertgl.getSelectedToggle().equals(male)) {
				gender = "Male";
			} else {
				gender = "Female";
			}
			SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date dates = java.sql.Date.valueOf(dob.getValue());
			String date = fomat.format(dates);
			
			String role = "user";
			
			String query = String.format("INSERT INTO ms_customer VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')", id , name , phone, address , email , password , gender , date , role);
			con.execUpdate(query);
						} while (con.res.next());
							
							
			
						
					
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Success Login");
					alert.showAndWait();
					stage.close();
					try {
						login.start(stage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		Scene scene = new Scene (border , 300 , 500);
		stage.setResizable(false);
		stage.setTitle("Register Form");
		stage.setScene(scene);
		Image logo = new Image("https://png.pngtree.com/template/20191019/ourlarge/pngtree-gm-letter-logo-vector-gm-initials-logo-designs-image_321122.jpg");
		stage.getIcons().add(logo);
		stage.show();
	}
	
	
	
	public void init() {
		stage = new Stage();
		
		border = new BorderPane();
		grid = new GridPane();
		flow = new FlowPane();
		flowgender = new FlowPane();

		male = new RadioButton("Male");
		female = new RadioButton("Female");
		gendertgl = new ToggleGroup();
		
		signUp2 = new Button("Sign Up");
		signIn2 = new Button("Sign In");
		
		flow.getChildren().add(signUp2);
		flow.getChildren().add(signIn2);
		
		register = new Label("Register");
		useridLbl = new Label("User ID:");
		usernameLbl = new Label("Username:");
		phoneLbl = new Label("Phone:");
		addressLbl = new Label("Address:");
		emailLbl = new Label("Email:");
		passwordLbl = new Label("Password:");
		dobLbl = new Label("Date Of Birth:");
		genderLbl = new Label("Gender:");
		
		userid = new TextField();
		username = new TextField();		
		username.setPromptText("Your Username");
		userphone = new TextField();
		userphone.setPromptText("Your Phone Number");
		useraddress = new TextField();
		useraddress.setPromptText("Your Address");
		useremail = new TextField();
		useremail.setPromptText("Your Email");
		userpassword = new PasswordField();
		userpassword.setPromptText("Your Password");
		dob = new DatePicker();
	    
	    
	    
	}


		public void position() {
			border.setTop(register);
			border.setCenter(grid);
			border.setBottom(flow);
			
			register.setFont(Font.font(30));
			
			male.setToggleGroup(gendertgl);
			female.setToggleGroup(gendertgl);
			flowgender.getChildren().add(male);
			flowgender.getChildren().add(female);
			flowgender.setOrientation(Orientation.VERTICAL);
			
			
			grid.add(useridLbl, 0, 0);
			grid.add(usernameLbl, 0, 1);
			grid.add(phoneLbl, 0, 2);
			grid.add(addressLbl, 0, 3);
			grid.add(emailLbl, 0, 4);
			grid.add(passwordLbl, 0, 5);
			grid.add(dobLbl, 0, 6);
			grid.add(genderLbl, 0, 7);
			
			grid.add(userid, 1, 0);
			grid.add(username, 1, 1);
			grid.add(userphone, 1, 2);
			grid.add(useraddress, 1, 3);
			grid.add(useremail, 1, 4);
			grid.add(userpassword, 1, 5);
			grid.add(dob, 1, 6);
			grid.add(flowgender, 1, 7);
			
			
			
			grid.setPadding(new Insets(0,0,0,20));
			flow.setPadding(new Insets(0,0,0,140));
			border.setMargin(flow, new Insets(15));
			
			
			grid.setVgap(15);
			grid.setHgap(15);
			
			flowgender.setVgap(5);
			flow.setHgap(15);
			flow.setVgap(15);
			
			
			register.setPadding(new Insets(20));
		}
		
		
	
	

	}
	
	

		


