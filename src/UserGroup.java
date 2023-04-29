import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserGroup {
    Set<User> members;

    public UserGroup(User... users) {
        this.members = new HashSet<>(Arrays.asList(users));
    }

    public Set<User> getUsers() {
        return this.members;
    }
}
