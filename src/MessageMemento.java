import java.util.ArrayList;
import java.util.List;

public class MessageMemento {
    private List<Message> state;

    public MessageMemento(List<Message> initialState) {
        this.state = initialState;
    }

    public void setState(List<Message> state) {
        this.state = new ArrayList<>(state);
    }

    public List<Message> getState() {
        return this.state;
    }
}
