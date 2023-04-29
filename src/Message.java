import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Message {
    private static long id = 0;
    private final long messageId;
    private final User sender;
    private final UserGroup group;
    private final long timestamp;
    private final String content;

    public Message(User sender, String content, UserGroup group) {
        this.messageId = id++;
        this.sender = sender;
        this.group = group;
        this.content = content;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public User getSender() {
        return sender;
    }

    public Set<User> getRecipients() {
        Set<User> recipients = this.chatGroup().getUsers();
        recipients.remove(this.getSender());
        return recipients;
    }

    public UserGroup chatGroup() {
        return group;
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
        output += String.format("To: %s\n", this.chatGroup().members.stream().map(user -> user.getName()).collect(Collectors.toList()));
        output += String.format("From: %s\n", this.getSender().getName());
        output += String.format("Contains: %s", this.getContent());
        return output;
    }

    public String toSimplifiedString() {
        String output = "";
        output += String.format("%s -> %s: [%s]", this.getSender().getName(), this.chatGroup().members.stream().map(user -> user.getName()).collect(Collectors.toList()), this.getContent());
        return output;
    }
}
