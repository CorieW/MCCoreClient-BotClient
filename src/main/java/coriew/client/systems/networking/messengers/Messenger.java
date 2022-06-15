package coriew.client.systems.networking.messengers;

import coriew.client.CoreBotClient;

// A messenger sends messages to the server through the websocket
// whenever an event happens.
public abstract class Messenger {
    public Messenger()
    {
        // Subscribe to events
        CoreBotClient.EVENT_BUS.subscribe(this);
    }
}
