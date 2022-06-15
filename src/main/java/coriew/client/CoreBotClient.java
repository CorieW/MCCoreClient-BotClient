package coriew.client;

import coriew.client.events.game.TickEvent;
import coriew.client.events.world.ChunkLoadEvent;
import coriew.client.systems.Systems;
import coriew.client.systems.networking.BotWebSocketClient;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.chunk.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;

public class CoreBotClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("corebotclient");
    public static CoreBotClient INSTANCE;
    public static MinecraftClient MC;
    public static final IEventBus EVENT_BUS = new EventBus();

    private Systems systems;

    @Override
    public void onInitializeClient()
    {
        INSTANCE = this;
        MC = MinecraftClient.getInstance();

        // Register event handlers
        EVENT_BUS.registerLambdaFactory("coriew.client", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        EVENT_BUS.subscribe(this);

        systems = new Systems();
    }
}
