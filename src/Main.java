import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        testSendingMessageWithOneRecipient();
        testSendingMessageWithTwoRecipients();
        testBlocking();
        testUnblocking();
        testGettingLastMessage();
        testRemovingMessage();
        testIterator();
    }

    public static void testSendingMessageWithOneRecipient() {
        System.out.println("Testing sending message with one recipient");

        // Setup
        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");

        UserGroup group = new UserGroup(user1, user2);

        user1.setChatServer(server);
        user2.setChatServer(server);

        // Send message to one person
        String messageContent = "Message";
        user1.sendMessage(messageContent, group);

        // Test
        assert user2.history.getChatHistory(group).size() == 1;
        assert user2.history.getChatHistory(group).get(0).getContent().equals(messageContent);

        user1.removeChatServer();
        user2.removeChatServer();

        System.out.println();
    }

    public static void testSendingMessageWithTwoRecipients() {
        System.out.println("Testing sending message with two recipients");

        // Setup
        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");
        User user3 = new User("Sean Smith");

        UserGroup group = new UserGroup(user1, user2, user3);

        user1.setChatServer(server);
        user2.setChatServer(server);
        user3.setChatServer(server);

        // Send message to two others
        String messageContent = "Message";
        user1.sendMessage("Message", group);

        // Test
        assert user2.history.getChatHistory(group).size() == 1;
        assert user2.history.getChatHistory(group).get(0).getContent().equals(messageContent);

        assert user3.history.getChatHistory(group).size() == 1;
        assert user3.history.getChatHistory(group).get(0).getContent().equals(messageContent);

        user1.removeChatServer();
        user2.removeChatServer();
        user3.removeChatServer();

        System.out.println();
    }

    public static void testBlocking() {
        System.out.println("Test blocking user");

        // Setup
        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");

        UserGroup group = new UserGroup(user1, user2);

        user1.setChatServer(server);
        user2.setChatServer(server);

        user1.sendMessage("Message1", group);
        user2.block(user1);
        user1.sendMessage("Message2", group);

        // Test
        assert user2.history.getChatHistory(group).size() == 1;
        assert user2.history.getChatHistory(group).get(0).getContent().equals("Message1");

        user1.removeChatServer();
        user2.removeChatServer();

        System.out.println();
    }

    public static void testUnblocking() {
        System.out.println("Test blocking user");

        // Setup
        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");

        UserGroup group = new UserGroup(user1, user2);

        user1.setChatServer(server);
        user2.setChatServer(server);

        user1.sendMessage("Message1", group);
        user2.block(user1);
        user2.unblock(user1);
        user1.sendMessage("Message2", group);

        // Test
        assert user2.history.getChatHistory(group).size() == 2;
        assert user2.history.getChatHistory(group).get(0).getContent().equals("Message1");
        assert user2.history.getChatHistory(group).get(1).getContent().equals("Message2");

        user1.removeChatServer();
        user2.removeChatServer();

        System.out.println();
    }

    public static void testShowChatHistory() {
        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");

        UserGroup group = new UserGroup(user1, user2);

        user1.setChatServer(server);
        user2.setChatServer(server);

        user1.sendMessage("Message 1", group);
        user1.sendMessage("Message 2", group);
        user2.sendMessage("Message 3", group);

        user1.history.printChatHistory(group);

        user1.removeChatServer();
        user2.removeChatServer();
    }

    public static void testGettingLastMessage() {
        System.out.println("Test getting last message");

        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");

        UserGroup group = new UserGroup(user1, user2);

        user1.setChatServer(server);
        user2.setChatServer(server);

        // Send message to one person
        user1.sendMessage("Message1", group);
        user1.sendMessage("Message2", group);

        assert user1.history.getLastMessage().getContent().equals("Message2");

        user1.removeChatServer();
        user2.removeChatServer();

        System.out.println();
    }

    public static void testRemovingMessage() {
        System.out.println("Test removing last sent message");

        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");

        UserGroup group = new UserGroup(user1, user2);

        user1.setChatServer(server);
        user2.setChatServer(server);

        user1.sendMessage("Message 1", group);
        user1.history.printChatHistory(group);
        user2.history.printChatHistory(group);

        user1.undoLastSentMessage();

        user1.history.printChatHistory(group);
        user2.history.printChatHistory(group);

        assert user1.history.getChatHistory(group).size() == 0;
        assert user1.sentMessages.size() == 0;
        assert user2.history.getChatHistory(group).size() == 0;

        user1.removeChatServer();
        user2.removeChatServer();

        System.out.println();
    }

    public static void testIterator() {
        System.out.println("Test iterator");

        ChatServer server = ChatServer.getInstance();

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");
        User user3 = new User("Sean Smith");

        UserGroup group1 = new UserGroup(user1, user2);
        UserGroup group2 = new UserGroup(user1, user3);

        user1.setChatServer(server);
        user2.setChatServer(server);
        user3.setChatServer(server);

        user1.sendMessage("Convo 1: 1", group1); // Conversation 1
        user1.sendMessage("Convo 2: 1", group2); // Conversation 2
        user2.sendMessage("Convo 1: 2", group1); // Conversation 1
        user3.sendMessage("Convo 2: 2", group2); // Conversation 2
        user1.sendMessage("Convo 1: 3", group1); // Conversation 1
        user1.sendMessage("Convo 2: 3", group2); // Conversation 2

        Iterator<Message> it = user1.iterator(user2);

        Message temp;
        assert it.hasNext();
        temp = it.next();
        assert temp.getContent().equals("Convo 1: 1");

        assert it.hasNext();
        temp = it.next();
        assert temp.getContent().equals("Convo 1: 2");

        assert it.hasNext();
        temp = it.next();
        assert temp.getContent().equals("Convo 1: 3");

        assert !it.hasNext();

        user1.removeChatServer();
        user2.removeChatServer();
        user3.removeChatServer();

        System.out.println();
    }

}