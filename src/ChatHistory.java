import java.util.*;

public class ChatHistory {
    public int userId;
    private List<Message> sentMessages;

    public ChatHistory() {
        this.sentMessages = new ArrayList<>();
    }

    public void addMessage(Message msg) {
        sentMessages.add(msg);
    }




}
