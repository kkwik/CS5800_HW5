import java.util.Set;
import java.util.stream.Collectors;

public class Message {
    private final User sender;
    private final Set<User> recipients;
    private final long timestamp;
    private final String content;

    public Message(User sender, String content, Set<User> recipients) {
        this.sender = sender;
        this.recipients = recipients;
        this.content = content;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public User getSender() {
        return sender;
    }

    public Set<User> getRecipients() {
        return recipients;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        String output = "";
        output += String.format("To: %s\n", this.getRecipients().stream().map(user -> user.getName()).collect(Collectors.toList()));
        output += String.format("From: %s\n", this.getSender().getName());
        output += String.format("Contains: %s\n", this.getContent());
        return output;
    }
}
