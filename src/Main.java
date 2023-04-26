import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        User user1 = new User("Joe Smith");
        User user2 = new User("Walter Smith");
        User user3 = new User("Kyle Smith");

        ChatServer server = new ChatServer();

        server.addUser(user1);
        server.addUser(user2);
        server.addUser(user3);

        server.sendMessage(new Message(user1, "Message 1", new HashSet<User>(Arrays.asList(user2, user3))));
        server.sendMessage(new Message(user2, "Message 2", new HashSet<>(Arrays.asList(user1))));
    }
}