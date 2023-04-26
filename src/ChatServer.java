import java.util.*;

public class ChatServer implements ChatServerMediator {
    List<User> users;
    Map<User, Set<User>> blocks;

    public ChatServer() {
        this.users = new ArrayList<>();
        this.blocks = new HashMap<>();
    }

    public void addUser(User user) {
        this.users.add(user);
        user.setChatServer(this);
    }

    public void sendMessage(Message msg) {
        for(User user : msg.getRecipients()) {
            if(canCommunicate(msg.getSender(), user))
                user.receiveMessage(msg);
        }
    }

    public void addBlock(User blocker, User blockee) {
        if(!blocks.containsKey(blocker)) {
            blocks.put(blocker, new HashSet<>());
        }

        Set<User> blockedUsers = blocks.get(blocker);
        blockedUsers.add(blockee);
        blocks.put(blocker, blockedUsers);
    }

    public void removeBlock(User blocker, User blockee) {
        if(blocks.containsKey(blocker)) {
            Set<User> blockedUsers = blocks.get(blocker);
            blockedUsers.remove(blockee);
            blocks.put(blocker, blockedUsers);
        }
    }

    private boolean canCommunicate(User user1, User user2) {
        return !communicationBlocked(user1, user2);
    }

    private boolean communicationBlocked(User user1, User user2) {
        return userBlockedUser(user1, user2) || userBlockedUser(user2, user1);
    }

    private boolean userBlockedUser(User blocker, User blockee) {
        return blocks.containsKey(blocker) && blocks.get(blocker).contains(blockee);
    }

    public void undoLastMessage(User user) {}


}
