import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private static int userCount;
    private int id;
    private String name;
    private ChatHistory history;
    private ChatServer server;

    public User(String name) {
        this.id = userCount++;
        this.name = name;
        this.history = new ChatHistory();
    }

    public void setChatServer(ChatServer server) {
        this.server = server;
    }

    public void sendMessage(String content, Set<User> recipients) {
        Message msg = new Message(this, content, recipients);
        server.sendMessage(msg);
    }

    public void receiveMessage(Message msg) {
        history.addMessage(msg);

        System.out.println(String.format("User %s recieved: [%s]", this.getName(), msg.toString()));
    }

    public void block(User user) {
        this.server.addBlock(this, user);
    }

    public String getName() {
        return this.name;
    }
}
