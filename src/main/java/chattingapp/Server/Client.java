package chattingapp.Server;

import java.io.IOException;

public class Client implements Runnable {
    private final Sockets connection;
    private final ChatRoom room;


    public Client(Sockets connection, ChatRoom room) {
        this.connection = connection;
        this.room = room;
    }



    public String read () throws IOException {
        return connection.read();
    }


    public void send(String message) {
        connection.send(message);
    }

    @Override
    public void run() {
        try {
            System.out.println("listening");
            String line = "";
            while ((line = connection.read()) != null) { // waits for text, if null stops listening
                if(line.equals("COMMAND:LOGOUT")) {
                    room.delete(this);
                    try {
                        connection.close();// closes connection for user
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                room.broadcast(line, this); // broadcasts to the server along with this (a reference to the current client)
            }

        } catch(IOException e) {
            System.out.println("client disconnected");
        } finally {
           room.delete(this);
           // deletes user from our list
            try {
                connection.close();// closes connection for user
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
