package coriew.client.events.server;

import net.minecraft.text.Text;

public class ChatMessageEvent {
    private static final ChatMessageEvent INSTANCE = new ChatMessageEvent();

    private Text message;
    private boolean modified;
    public int id;

    public static ChatMessageEvent get(Text message, int id) {
        INSTANCE.message = message;
        INSTANCE.modified = false;
        INSTANCE.id = id;
        return INSTANCE;
    }

    public Text getMessage() {
        return message;
    }

    public void setMessage(Text message) {
        this.message = message;
        this.modified = true;
    }

    public boolean isModified() {
        return modified;
    }
}