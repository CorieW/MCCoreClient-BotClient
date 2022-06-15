package coriew.client.systems.networking;

import java.net.URI;
import java.util.Map;

import coriew.client.systems.networking.receivers.Receivers;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

public class BotWebSocketClient extends WebSocketClient {
    private static BotWebSocketClient INSTANCE;
    private Receivers receivers;

    public BotWebSocketClient(URI serverUri, Draft draft, Receivers receivers) {
        super(serverUri, draft);
        this.receivers = receivers;
    }

    public BotWebSocketClient(URI serverURI, Receivers receivers) {
        super(serverURI);
        this.receivers = receivers;
    }

    public BotWebSocketClient(URI serverUri, Map<String, String> httpHeaders, Receivers receivers) {
        super(serverUri, httpHeaders);
        this.receivers = receivers;
        this.INSTANCE = this;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("opened connection");
        while (true)
        {
            System.out.println("cool");
        }
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    // Returns BotWebSocketClient instance if one exists and is connected successfully.
    // Otherwise, throws an error.
    public static BotWebSocketClient getInstance() throws Exception
    {
        if (INSTANCE == null || !INSTANCE.isOpen())
        {
            throw new Exception();
        }
        return INSTANCE;
    }
}
