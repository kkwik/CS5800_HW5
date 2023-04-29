import java.util.*;
import java.util.stream.Collectors;

public class ChatHistory implements IterableByUser {
    private User owner;
    private Map<UserGroup, List<Message>> history;

    public ChatHistory(User user) {
        this.owner = user;
        this.history = new HashMap<>();
    }

    public void addMessage(Message msg) {
        UserGroup group = msg.chatGroup();
        if(!history.containsKey(group)) {
            history.put(group, new ArrayList<Message>());
        }

        List<Message> messages = history.get(group);
        messages.add(msg);
        history.put(group, messages);
    }

    public void removeMessage(Message msg) {
        UserGroup group = msg.chatGroup();
        if(!history.containsKey(group)) {
            return;
        }

        List<Message> messages = history.get(group);
        messages.remove(msg);
        history.put(group, messages);
    }

    public Message getLatestMessageFromChat(UserGroup group) {
        return history.get(group).get(0);
    }

    public Message getLastMessage() {
        return history.values().stream().flatMap(chatList -> chatList.stream()).reduce((a,b) -> a.getTimestamp() > b.getTimestamp() ? a : b).get();
    }

    public List<Message> getChatHistory(UserGroup group) {
        return history.get(group);
    }

    public void printChatHistory(UserGroup group) {
        System.out.println(String.format("Chat history for [%s]:", owner.getName()));
        List<Message> chat = history.get(group);
        for(Message msg : chat) {
            System.out.println(msg.toSimplifiedString());
        }
    }

    public Iterator iterator(User userToSearchWith) {
        List<Message> allHistoryMessages = history.values().stream().flatMap(chatList -> chatList.stream()).collect(Collectors.toList());
        List<Message> allUserInvolvedMessages = allHistoryMessages.stream().filter((msg) -> msg.chatGroup().getUsers().contains(userToSearchWith)).collect(Collectors.toList());
        List<Message> userInvolvedMessagesChronological = allUserInvolvedMessages.stream().sorted(Comparator.comparingLong(Message::getTimestamp)).collect(Collectors.toList());

        return userInvolvedMessagesChronological.iterator();
    }
}
