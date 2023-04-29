public interface ChatServerMediator {
    public void addUser(User user);
    public void removeUser(User user);
    public void sendMessage(Message msg);
    public void removeMessage(Message msg);
    public void addBlock(User initiator, User blockee);
    public void removeBlock(User blocker, User blockee);
}
