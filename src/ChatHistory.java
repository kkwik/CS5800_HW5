import java.util.*;

public class ChatHistory {
    public int userId;
    private Map<Set<User>, List<Message>> history;

    public ChatHistory() {
        this.history = new HashMap<>();
    }

    public void addMessage(Message msg) {
        Set<User> recipients = msg.getRecipients();
        if(!history.containsKey(recipients)) {
            history.put(recipients, new ArrayList<Message>());
        }

        List<Message> messages = history.get(recipients);
        messages.add(msg);
        history.put(recipients, messages);
    }
}
