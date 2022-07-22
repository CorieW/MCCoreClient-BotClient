package coriew.client.events.entity;

import net.minecraft.entity.Entity;

public class EntitySpawnEvent {
    private static final EntitySpawnEvent INSTANCE = new EntitySpawnEvent();

    public Entity entity;

    public static EntitySpawnEvent get(Entity entity) {
        INSTANCE.entity = entity;
        return INSTANCE;
    }
}
