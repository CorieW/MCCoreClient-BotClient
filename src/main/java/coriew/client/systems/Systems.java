package coriew.client.systems;

import coriew.client.systems.networking.Networking;

import java.util.ArrayList;
import java.util.List;

public class Systems {
    private Networking networking;

    public Systems()
    {
        this.networking = new Networking();
    }
}
