package coriew.client.systems.networking.messengers;

import coriew.client.events.world.ChunkLoadEvent;
import coriew.client.systems.networking.BotWebSocketClient;
import coriew.client.utils.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChunkLoadMessenger extends Messenger {
    @EventHandler
    private void onChunkLoad(ChunkLoadEvent event)
    {
        BotWebSocketClient client = null;
        try {
            client = BotWebSocketClient.getInstance();
            client.send("hello");
        } catch (Exception e) { return; }

        // Send chunk to server
    }
}
