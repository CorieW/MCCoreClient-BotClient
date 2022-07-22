package coriew.client.events.server;

public class GameJoinEvent {
    private static final GameJoinEvent INSTANCE = new GameJoinEvent();

    public static GameJoinEvent get() {
        return INSTANCE;
    }
}