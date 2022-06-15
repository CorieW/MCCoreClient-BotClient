package coriew.client.events.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class EntityDamageEvent {
    private static final EntityDamageEvent INSTANCE = new EntityDamageEvent();

    public LivingEntity entity;
    public DamageSource source;

    public static EntityDamageEvent get(LivingEntity entity, DamageSource source) {
        INSTANCE.entity = entity;
        INSTANCE.source = source;
        return INSTANCE;
    }
}