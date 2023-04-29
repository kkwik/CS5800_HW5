import java.util.*;

public class User implements IterableByUser {
    private static int userCount;
    private int id;
    private String name;
    protected ChatHistory history;
    protected List<Message> sentMessages;
    private MessageMemento memento;
    private ChatServer server;

    public User(String name) {
        this.id = userCount++;
        this.name = name;
        this.history = new ChatHistory(this);
        this.sentMessages = new ArrayList<>();
        this.memento = new MessageMemento(this.sentMessages);
    }

    public void setChatServer(ChatServer server) {
        this.server = server;
        server.addUser(this);
    }

    public void removeChatServer() {
        server.removeUser(this);
        this.server = null;
    }

    public void sendMessage(String content, UserGroup group) {
        this.memento.setState(this.sentMessages);
        Message msg = new Message(this, content, group);
        server.sendMessage(msg);
        this.sentMessages.add(msg);
    }

    public void receiveMessage(Message msg) {
        history.addMessage(msg);

        System.out.println(String.format("%s recieved [%s]", this.getName(), msg.toSimplifiedString()));
    }

    public void undoLastSentMessage() {
        Message lastSentMessage = this.lastSentMessage();

        this.sentMessages = memento.getState();
        this.history.removeMessage(lastSentMessage);
        server.removeMessage(lastSentMessage);
    }

    public Message lastSentMessage() {
        return this.sentMessages.get(this.sentMessages.size() - 1);
    }

    public void removeRecievedMessage(Message msg) {
        history.removeMessage(msg);
    }

    public void block(User user) {
        this.server.addBlock(this, user);
    }

    public void unblock(User user) {
        this.server.removeBlock(this, user);
    }

    public String getName() {
        return this.name;
    }

    public Iterator iterator(User userToSearchWith) {
        return this.history.iterator(userToSearchWith);
    }
}
