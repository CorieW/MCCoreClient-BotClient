package coriew.client.systems.networking;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import coriew.client.CoreBotClient;
import coriew.client.systems.networking.messengers.Messengers;
import coriew.client.systems.networking.receivers.Receivers;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Networking {
    public static Networking INSTANCE;

    private BotWebSocketClient wsClient;
    private Messengers messengers;
    private Receivers receivers;

    public Networking()
    {
        INSTANCE = this;
        messengers = new Messengers();
        receivers = new Receivers();
    }

    public void SendLoginRequest(String username, String password) throws Exception
    {
        String sessionToken = "Test";

        HttpClientContext context = HttpClientContext.create();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create POST login request with data as parameters
            HttpPost postReq = new HttpPost("http://localhost:3000/api/login");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("email", username));
            params.add(new BasicNameValuePair("password", password));
            postReq.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            // Execute POST login request
            try (CloseableHttpResponse response = httpClient.execute(postReq, context)) {
                HttpEntity entity = response.getEntity();
                CookieStore cookieStore = context.getCookieStore();
                Optional<Cookie> customCookie = cookieStore.getCookies()
                        .stream()
                        .filter(cookie -> "sessionToken".equals(cookie.getName()))
                        .findFirst();

                // Parse JSON response to map
                Gson gson = new Gson();
                Map<String, String> map = gson.fromJson(EntityUtils.toString(entity, "UTF-8"), Map.class);

                // Response
                String message = map.get("message");
                String error = map.get("error");

                // Throw errors if anything goes wrong or login attempt is unsuccessful
                if (entity == null)
                {
                    throw new Exception("Something went wrong!");
                }
                if (error != null)
                {
                    throw new Exception(message);
                }

                // Set the session token
                sessionToken = cookieStore.getCookies().get(0).getValue();
            }
        }

        // Connect to socket
        if(sessionToken != null)
        {
            try {
                // Add the session token cookie
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie", "sessionToken=" + sessionToken);

                // Create the websocket client
                BotWebSocketClient webSocketClient = new BotWebSocketClient(
                        new URI("ws://localhost:8080/socket?clientType=bot"),
                        headers,
                        receivers
                );

                // Connect the websocket client to the websocket server
                webSocketClient.connect();

            } catch (URISyntaxException ex) { }
        }
    }
}
