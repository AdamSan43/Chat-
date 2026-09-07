package chattingapp.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ChatServer {
    public static void main(String[] args) {
        ChatRoom room;
        ServerSocket serverSocket;

        try {

        room = new ChatRoom();
        int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
        serverSocket = new ServerSocket(serverPort);
        System.out.println("server");

            while (true) {
                System.out.println("up");
                Socket clientSocket = serverSocket.accept();
                Sockets connection = new Sockets();
                connection.initConnection(clientSocket);
                Client client = new Client(connection, room);
                room.add(client);
                System.out.println("Connected");
                new Thread(client).start();

                //hands off to client (run)  (only focuses on accepting new users)

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




