package coriew.client.systems.networking.messengers;

import java.util.ArrayList;

public class Messengers {
    private ArrayList<Messenger> receivers = new ArrayList<Messenger>();

    public Messengers()
    {
        add(new ChunkLoadMessenger());
    }

    private void add(Messenger messenger)
    {
        receivers.add(messenger);
    }
}
