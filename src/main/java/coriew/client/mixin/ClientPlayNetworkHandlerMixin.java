package coriew.client.mixin;

import coriew.client.CoreBotClient;
import coriew.client.events.server.GameJoinEvent;
import coriew.client.events.server.GameLeftEvent;
import coriew.client.events.world.ChunkDataEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private ClientWorld world;

    private boolean worldNotNull;

    @Inject(at = @At("HEAD"), method = "onGameJoin")
    private void onGameJoinHead(GameJoinS2CPacket packet, CallbackInfo info) {
        worldNotNull = world != null;
    }

    @Inject(at = @At("TAIL"), method = "onGameJoin")
    private void onGameJoinTail(GameJoinS2CPacket packet, CallbackInfo info) {
        if (worldNotNull) {
            CoreBotClient.EVENT_BUS.post(GameLeftEvent.get());
        }

        CoreBotClient.EVENT_BUS.post(GameJoinEvent.get());
    }

    @Inject(method = "onChunkData", at = @At("TAIL"))
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo info) {
//        Dat d = new Dat();
//        ClientPlayNetworking.
//        d.stuff = packet.getChunkData().getSectionsDataBuf();
//        Gson gson = new Gson();
//        try {
//            BotWebSocketClient.getInstance().send(gson.toJson(d));
//        } catch(Exception e) { }

        WorldChunk chunk = client.world.getChunk(packet.getX(), packet.getZ());
        CoreBotClient.EVENT_BUS.post(ChunkDataEvent.get(chunk));
    }
}