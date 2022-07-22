package coriew.client.systems.networking.messengers;

public class Message<T> {
    public String type;
    public T data;

    public Message(String type, T data)
    {
        this.type = type;
        this.data = data;
    }
}
