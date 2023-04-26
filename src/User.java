import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private static int userCount;
    private int id;
    private String name;
    private ChatServer server;

    public User(String name) {
        this.id = userCount++;
        this.name = name;
    }

    public void setChatServer(ChatServer server) {
        this.server = server;
        server.addUser(this);
    }

    public void removeChatServer() {
        this.server = null;
        server.removeUser(this);
    }

    public void sendMessage(String content, Set<User> recipients) {
        Message msg = new Message(this, content, recipients);
        server.sendMessage(msg);
    }

    public void receiveMessage(Message msg) {
        System.out.println(String.format("[%s]", msg.toSimplifiedString()));
    }

//    public void undoLastSentMessage() {
//        Message lastSentMessage = this.history.getLastSentMessage(this);
//        server.undoLastMessage(lastSentMessage);
//    }
//
//    public void removeMessage(Message msg) {
//        history.removeMessage(msg);
//    }

    public void block(User user) {
        this.server.addBlock(this, user);
    }

    public void unblock(User user) {
        this.server.removeBlock(this, user);
    }

    public String getName() {
        return this.name;
    }
}
