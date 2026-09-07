package chattingapp.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Sockets {
    private BufferedReader bufferedReader;
    private  PrintWriter printWriter;
    private Socket socket;
    String username;



    public void initConnection(Socket socket) throws IOException {
        this.socket = socket;
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        this.bufferedReader = new BufferedReader(inputStreamReader);
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);

    }

    public void getUsername(String username) {
         this.username = username;
    }

public String read() throws IOException {

        return bufferedReader.readLine();
}

public void send(String message) {

            printWriter.println(username + ": " + message);
        }

public void close() throws IOException {
        socket.close();
}

    public void sendLogoutMessage() throws IOException {
        printWriter.println("COMMAND:LOGOUT");
        printWriter.flush();
    }





}
