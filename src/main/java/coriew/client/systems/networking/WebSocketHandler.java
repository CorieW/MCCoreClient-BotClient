package coriew.client.systems.networking;

import java.net.URI;
import java.util.Map;

import com.google.gson.Gson;
import coriew.client.CoreBotClient;
import coriew.client.systems.networking.messengers.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

// Acts as a kind of wrapper for the BotWebSocketClient, to remove some
// unnecessary functionality and provide some use-case based customization
// on top.
public class WebSocketHandler {
    private static WebSocketHandler INSTANCE;

    private BotWebSocketClient wsClient;
    public WebSocketHandler(BotWebSocketClient wsClient)
    {
        INSTANCE = this;

        this.wsClient = wsClient;
    }

    public void connect()
    {
        if (wsClient.isOpen()) return;

        wsClient.connect();
    }

    public void send(Message message) throws Exception
    {
        if (!wsClient.isOpen()) throw new Exception();

        Gson gson = new Gson();
        String json = gson.toJson(message);
        wsClient.send(json);
    }

    // Returns WebSocketHandler instance if one exists and websocket is connected successfully.
    // Otherwise, throws an error.
    public static WebSocketHandler getInstance() throws Exception {
        if (INSTANCE == null || !INSTANCE.wsClient.isOpen()) {
            throw new Exception();
        }
        return INSTANCE;
    }

    public static class BotWebSocketClient extends WebSocketClient {
        private Receiver receiver;

        public BotWebSocketClient(URI serverUri, Draft draft, Receiver receiver) {
            super(serverUri, draft);
            this.receiver = receiver;
        }

        public BotWebSocketClient(URI serverURI, Receiver receiver) {
            super(serverURI);
            this.receiver = receiver;
        }

        public BotWebSocketClient(URI serverUri, Map<String, String> httpHeaders, Receiver receiver) {
            super(serverUri, httpHeaders);
            this.receiver = receiver;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println("opened connection");
            // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
        }

        @Override
        public void onMessage(String message) {
            System.out.println("received: " + message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            CoreBotClient.LOGGER.info("closing connection!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
            // if the error is fatal then onClose will be called additionally
        }
    }
}
