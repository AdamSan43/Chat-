package chattingapp.controller;
import chattingapp.Server.Sockets;
import chattingapp.database.HandleDatabase;
import chattingapp.database.LoginHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;


public class LoginScreenController {
    HandleDatabase database = new HandleDatabase();
    LoginHelper login = new LoginHelper();
    public static String currentUser = "";



    //login fxml

    @FXML
    Pane loginPane;

    @FXML
    Label loginErrorLabel;

    @FXML
    PasswordField passwordTxtField;

    @FXML
    TextField usernameTxtField;

    @FXML
    Button loginButton;


    // user creation fxml

    @FXML
    Button createButton;
    @FXML
    TextField userCreationPass;
    @FXML
    TextField userCreationUser;

    @FXML
    Button createAccountButton;

    @FXML
    TextField confirmPass;

    @FXML
    Label creationErrorLabel;

    @FXML
     Pane createUserPane;

    @FXML
    Pane popupPane;

    @FXML
    Button popupButton;




    @FXML
    void loginButtonClicked (MouseEvent event) throws Exception {
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();


        System.out.println(username);
        try {
            if(database.login(username,password)) {
                System.out.println("LOGGED IN");
                Sockets socket = new Sockets();
                String serverHost = System.getenv("SERVER_HOST");
                int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));

                Socket rawSocket = new Socket(serverHost, serverPort);
                socket.initConnection(rawSocket);
                socket.getUsername(username);


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/chattingapp/Chat!.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 600, 400);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("globalchat");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

                MainScreenController mainScreenController = loader.getController();
                mainScreenController.initClient(socket, username,database);
                new Thread(mainScreenController).start();



            } else {
                System.out.println("NOT LOGGED IN");
                loginErrorLabel.setText("Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }





    @FXML
    void createButtonClicked(MouseEvent event) throws IOException {
        creationErrorLabel.setText("");
        userCreationUser.setText("");
        userCreationPass.setText("");
        confirmPass.setText("");


        loginPane.setVisible(false);
        loginPane.setManaged(false);
        loginPane.setDisable(true);
        createUserPane.setDisable(false);
        createUserPane.setVisible(true);
        createUserPane.setManaged(true);
        System.out.println("Button was successfully clicked!");

    }
    @FXML
    void createAccountButtonClicked(MouseEvent event) throws IOException {
        String username = userCreationUser.getText();
        String password = userCreationPass.getText();
        String confirm = confirmPass.getText();
        loginErrorLabel.setText("");


        if(password.equals(confirm)) {
            if(login.userNameRules(username) && login.passwordRules(password)) {
                try {
                    if(!database.publicuserExists(username)) {
                        database.publicCreateUser(username,password);
                        System.out.println("USER CREATED");


                        //a popup that shows up when account is created successfully
                        popupPane.setDisable(false);
                        popupPane.setVisible(true);
                        popupPane.setManaged(true);

                        //makes createaccount screen visible
                        createUserPane.setVisible(false);
                        createUserPane.setManaged(false);
                        createUserPane.setDisable(true);


                        // redirects to login page after account creation
                        loginPane.setVisible(true);
                        loginPane.setManaged(true);
                        loginPane.setDisable(false);

                    } else {
                        creationErrorLabel.setText("Username is already taken");
                        System.out.println("USER EXISTS ALREADY");
                    }

                } catch(SQLException e) {
                    e.printStackTrace();
                }

        } else {
                creationErrorLabel.setText("Password must contain a symbol !-+ and be 8 characters long");
                System.out.println("contains no symbol or too short");
            }
        } else if(!password.equals(confirm)) {
            System.out.println("NOT MATCHING");
            creationErrorLabel.setText("Passwords don't match");
        }

        }

        @FXML
    void popupButtonClicked(MouseEvent event) {
        System.out.println("clicked");

            popupPane.setDisable(true);
            popupPane.setVisible(false);
            popupPane.setManaged(false);
        }
        @FXML
    void backButtonClicked (MouseEvent event) {

            createUserPane.setVisible(false);
            createUserPane.setManaged(false);
            createUserPane.setDisable(true);

            loginPane.setVisible(true);
            loginPane.setManaged(true);
            loginPane.setDisable(false);



        }




    }







