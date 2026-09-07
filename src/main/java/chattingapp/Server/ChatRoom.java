package chattingapp.Server;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ChatRoom {
    private final List<Client> clients = new CopyOnWriteArrayList<>();

    public void add(Client handleClient) {
        clients.add(handleClient);
    }

    public void delete(Client handleClient) {
        clients.remove(handleClient);
    }


public void broadcast(String message, Client sender) throws IOException {
    for(Client client : clients) {

            client.send(message);

    }

}

}
