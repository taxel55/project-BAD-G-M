
import java.sql.SQLException;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Application {
	BorderPane borderPane ;
	GridPane gridPane ;
	Label emailLbl , passwordLbl , tittle;
	TextField email;
	PasswordField password;
	Button signUp , signIn;
	FlowPane button;
	StackPane stackPane;
	Scene scene;
	Connection con = Connection.getInstance();
	Vector<Customer> user = new Vector<>();
	
	public void init() {
		gridPane = new GridPane();
		emailLbl = new Label("Email:");
		passwordLbl = new Label("Password:");
		tittle = new Label("Login");
		
		email = new TextField();
		email.setPromptText("Your Email");
		
		password = new PasswordField();
		password.setPromptText("Your Password");
		
		signUp = new Button("Sign Up");
		signIn = new Button("Sign In");
		button = new FlowPane();
		borderPane = new BorderPane();
		stackPane = new StackPane();
		scene = new Scene(stackPane,280,170);
	}
	
	public void position() {
		stackPane.getChildren().add(borderPane);
		button.getChildren().add(signUp);
		button.getChildren().add(signIn);
		tittle.setFont(Font.font(30));
		borderPane.setTop(tittle);
		borderPane.setCenter(gridPane);
		borderPane.setBottom(button);
		
		gridPane.add(emailLbl, 0, 0);
		gridPane.add(passwordLbl, 0, 1);
		gridPane.add(email, 1, 0);
		gridPane.add(password, 1, 1);
		
		
		tittle.setPadding(new Insets(0,0,0,20));
		gridPane.setPadding(new Insets(0,0,0,20));
		button.setPadding(new Insets(0,0,0,100));
		gridPane.setVgap(20);
		gridPane.setHgap(20);
		
		button.setHgap(15);
		button.setVgap(15);
	
		borderPane.setMargin(button, new Insets(15));
		
	
	}

	
	

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
		init();
		position();

		signIn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				String emails = email.getText();
				String pass = password.getText();
				String query = String.format("SELECT * FROM ms_customer WHERE email = '%s' AND password = '%s'", emails , pass);
				
				con.res = con.execQuery(query);
				
				
				try {

					if(con.res.next()) {
						String userrole = con.res.getString("role");
						Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setContentText("Success Login");
							alert.showAndWait();
							if (userrole.equalsIgnoreCase("admin")) {
								new AdminMenu();
								arg0.close();
							}
							else if (userrole.equalsIgnoreCase("user")) {
								new UserMenu(con.res.getString("id"));
								arg0.close();
								
								
							}
							
					}else if(!email.getText().matches("^(.+)@(.+)$*")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Wrong Email Format");
						alert.show();
					}else if(!(password.getText().length()>6 || password.getText().length()<12) ) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Password must be 6-12 character");
						alert.show();
					}else if(!password.getText().matches("^[a-zA-Z0-9]+$")){
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Wrong Password Format");
						alert.show();
					}else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Not Registered");
						alert.show();
					}
								
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					
				} 

				
		
			}
		});
		
		signUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				arg0.close();
				new Register();
			}
			
		});

		arg0.setResizable(false);
		arg0.setTitle("Login Form");
		Image logo = new Image("https://png.pngtree.com/template/20191019/ourlarge/pngtree-gm-letter-logo-vector-gm-initials-logo-designs-image_321122.jpg");
		arg0.getIcons().add(logo);
		arg0.setScene(scene);
		
		
		arg0.show();
		
		
	
		
		
		
		
	}
	
	
}