import java.util.*;
import java.util.stream.Collectors;

public class ChatHistory {
    private Map<Set<User>, List<Message>> history;

    public ChatHistory() {
        this.history = new HashMap<>();
    }

    public void addMessage(Message msg) {
        Set<User> members = msg.getChatMembers();
        if(!history.containsKey(members)) {
            history.put(members, new ArrayList<Message>());
        }

        List<Message> messages = history.get(members);
        messages.add(msg);
        history.put(members, messages);
    }

    public void removeMessage(Message msg) {
        Set<User> members = msg.getChatMembers();
        if(!history.containsKey(members)) {
            return;
        }

        List<Message> messages = history.get(members);
        messages.remove(msg);
        history.put(members, messages);
    }

    public Message getLatestMessageFromChat(Set<User> members) {
        return history.get(members).get(0);
    }

    public Message getLastMessage() {
        return history.values().stream().flatMap(chatList -> chatList.stream()).reduce((a,b) -> a.getTimestamp() > b.getTimestamp() ? a : b).get();
    }

}
