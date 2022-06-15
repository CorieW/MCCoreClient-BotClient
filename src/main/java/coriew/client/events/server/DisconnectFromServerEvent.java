package coriew.client.events.server;

public class DisconnectFromServerEvent {
    private static final DisconnectFromServerEvent INSTANCE = new DisconnectFromServerEvent();

    public static DisconnectFromServerEvent get() {
        return INSTANCE;
    }
}
