package coriew.client.mixin;

import coriew.client.CoreBotClient;
import coriew.client.events.game.TickEvent;
import coriew.client.events.server.GameLeftEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftClient.class, priority = 1001)
public abstract class MinecraftClientMixin {

    @Shadow public ClientWorld world;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        CoreBotClient.INSTANCE.onInitializeClient();
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onPreTick(CallbackInfo info) {
        CoreBotClient.EVENT_BUS.post(TickEvent.Pre.get());
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void onPostTick(CallbackInfo info) {
        CoreBotClient.EVENT_BUS.post(TickEvent.Post.get());
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void onDisconnect(Screen screen, CallbackInfo info) {
        if (world != null) {
            CoreBotClient.EVENT_BUS.post(GameLeftEvent.get());
        }
    }
}