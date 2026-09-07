package chattingapp.controller;
import chattingapp.Server.Sockets;
import chattingapp.database.HandleDatabase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.paint.Color;


public class MainScreenController implements Runnable  {
    private Sockets socket;
    private HandleDatabase database;

    @FXML
    TextField messageField;

    @FXML
    TextArea globalChat;



    @FXML
    Button sendButton;

     @FXML
     Button LogoutButton;

    String username;


    public void initClient(Sockets socket, String username, HandleDatabase database) {
        this.database = database;
        this.socket = socket;
        this.username = username;
        globalChat.setStyle("-fx-text-fill: black;");
        socket.send("has joined!");
    }




    @Override
    public void run () {
         String line = "";
        try {
            while ((line = socket.read()) != null) {
                String temp = line;

                //save chats



                Platform.runLater(() -> {
                    String cleanOutput = temp.replaceAll("null:", "");
                    if(!cleanOutput.trim().endsWith("COMMAND:LOGOUT")) {
                        globalChat.appendText(cleanOutput + "\n");
                    }

                });
            }
        } catch(IOException e) {
            e.printStackTrace();

        }


    }


    public void sendMessage(String message) throws SQLException {
        socket.send(message);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = currentDateTime.format(formatter);
        try {
            database.logMessage(username, message, time);
        } catch(SQLException e) {
            e.printStackTrace();
        }



    }




    @FXML
    void sendButtonClicked(MouseEvent input) throws Exception {
        String string = messageField.getText();
        sendMessage(string);
        messageField.setText("");
        System.out.println("send button");

    }

    @FXML
    void messageFieldKeyPressed(KeyEvent keyEvent) throws Exception {
        if(keyEvent.getCode()== KeyCode.ENTER) {
            String string = messageField.getText();
            sendMessage(string);
            messageField.setText("");


        }
    }


    @FXML
    void LogoutButtonClicked(MouseEvent input) throws Exception {

        socket.send("Has left the chatroom!");
        try {
            socket.sendLogoutMessage();
        } catch(IOException e) {
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chattingapp/LoginScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);

        Stage stage = (Stage) ((Node) input.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }












}





