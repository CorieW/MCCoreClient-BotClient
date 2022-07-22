package coriew.client.events.server;

public class GameLeftEvent {
    private static final GameLeftEvent INSTANCE = new GameLeftEvent();

    public static GameLeftEvent get() {
        return INSTANCE;
    }
}
