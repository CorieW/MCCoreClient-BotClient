package coriew.client.systems.networking.messengers;

import com.google.gson.Gson;
import coriew.client.CoreBotClient;
import coriew.client.events.world.ChunkDataEvent;
import coriew.client.systems.networking.WebSocketHandler;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.WorldChunk;

import java.util.concurrent.PriorityBlockingQueue;

public class ChunkLoadMessenger extends Messenger {
//    private PriorityBlockingQueue<WorldChunk> in = new PriorityBlockingQueue<WorldChunk>();
//    private PriorityBlockingQueue<Chunk> out = new PriorityBlockingQueue<Chunk>();

    @EventHandler
    private void onChunkLoad(ChunkDataEvent event)
    {
//        in.put(event.chunk);

        WebSocketHandler wsHandler = null;
        try {
            wsHandler = WebSocketHandler.getInstance();

            Gson gson = new Gson();
            Chunk chunk = new Chunk(event.chunk);
            wsHandler.send(new Message<Chunk>("ChunkLoad", chunk));
        } catch (Exception e) { return; }
    }

    private class ChunkPos
    {
        public int x;
        public int z;

        public ChunkPos(net.minecraft.util.math.ChunkPos mcChunkPos)
        {
            this.x = mcChunkPos.x;
            this.z = mcChunkPos.z;
        }
    }
    private class Chunk
    {
        public ChunkLoadMessenger.ChunkPos pos;
        public int[] blockStates;

        public Chunk(net.minecraft.world.chunk.WorldChunk mcChunk)
        {
            this.pos = new ChunkLoadMessenger.ChunkPos(mcChunk.getPos());

            ChunkSection[] chunkSections = mcChunk.getSectionArray();
            blockStates = new int[16 * 16 * 16 * chunkSections.length];

            int i = 0;
            for (ChunkSection chunkSection : chunkSections)
            {
                PalettedContainer<BlockState> blockStateContainer = chunkSection.getBlockStateContainer();

                for (int y = 0; y < 16; y++)
                {
                    for (int z = 0; z < 16; z++)
                    {
                        for (int x = 0; x < 16; x++, i++)
                        {
                            blockStates[i] = Block.getRawIdFromState(blockStateContainer.get(x, y, z));
//                            asStr += blockStates[i] + ", ";
                        }
                    }
                }
            }
        }
    }
}
