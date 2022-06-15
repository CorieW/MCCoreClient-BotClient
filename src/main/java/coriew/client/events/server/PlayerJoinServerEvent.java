package coriew.client.events.server;

public class PlayerJoinServerEvent {
    private static final PlayerJoinServerEvent INSTANCE = new PlayerJoinServerEvent();

    public static PlayerJoinServerEvent get() {
        return INSTANCE;
    }
}