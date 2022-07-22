package coriew.client.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import coriew.client.CoreBotClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.PlayerSkinProvider;

import java.util.Map;

public final class URLHelper
{
    public static String GetDomain()
    {
        return Global.ProdEnv ? "url.com" : "localhost:3000";
    }

    public static String GetAddress()
    {
        return Global.ProdEnv ? String.format("https://%s", GetDomain()) : String.format("http://%s", GetDomain());
    }

    public static String GetAPIAddress()
    {
        return Global.ProdEnv ? String.format("https://api.%s", GetDomain()) : String.format("http://%s/api/", GetDomain());
    }

    public static String GetWebSocketAddress(boolean includeQuery)
    {
        String url = Global.ProdEnv ? String.format("wss://%s/socket", GetDomain()) : "ws://localhost:8080/socket";
        String mcVersion = MinecraftClient.getInstance().getGame().getVersion().getId();
        String mcUsername = MinecraftClient.getInstance().getSession().getUsername();
        GameProfile gameProfile = MinecraftClient.getInstance().getSession().getProfile();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> skinTexture = MinecraftClient.getInstance().getSkinProvider().getTextures(gameProfile);
        CoreBotClient.LOGGER.info(skinTexture.toString());
        if (includeQuery) url += String.format("?clientType=bot&clientVersion=%s&mcVersion=%s&mcUsername=%s", Global.ClientVersion, mcVersion, mcUsername);
        return url;
    }
}
