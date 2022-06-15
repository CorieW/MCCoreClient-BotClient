package coriew.client.events.server;

public class PlayerLeftServerEvent {
    private static final PlayerLeftServerEvent INSTANCE = new PlayerLeftServerEvent();

    public static PlayerLeftServerEvent get() {
        return INSTANCE;
    }
}