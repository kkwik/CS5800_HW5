public interface ChatServerMediator {
    public void sendMessage(Message msg);
    public void undoLastMessage(User user);
    public void addBlock(User initiator, User blockee);
}
