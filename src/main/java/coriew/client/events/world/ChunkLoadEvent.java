package coriew.client.events.world;

import net.minecraft.world.chunk.Chunk;

public class ChunkLoadEvent {
    private static final ChunkLoadEvent INSTANCE = new ChunkLoadEvent();

    public Chunk chunk;

    public static ChunkLoadEvent get(Chunk chunk) {
        INSTANCE.chunk = chunk;
        return INSTANCE;
    }
}
