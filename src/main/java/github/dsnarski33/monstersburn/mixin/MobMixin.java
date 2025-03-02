package github.dsnarski33.monstersburn.mixin;

import github.dsnarski33.monstersburn.MonstersBurn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity
{
    @Unique
    boolean monstersburn$isMobForcedBurn = false;

    protected MobMixin(EntityType<? extends LivingEntity> $$0, Level $$1) { super($$0, $$1); }

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void init(CallbackInfo ci) {
        Mob self = (Mob)(Object)this;
        if (self instanceof Monster || self instanceof Enemy)
            monstersburn$isMobForcedBurn = true;
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    public void aiStep(CallbackInfo ci) {
        if (monstersburn$isMobForcedBurn && !level().isClientSide && level().isDay() && isAlive() && !(isInPowderSnow || wasInPowderSnow) && MonstersBurn.isValidDimension(level())) {
            boolean isInWaterRainOrBubble = isInWaterRainOrBubble();
            if(MonstersBurn.isOnSurfaceMobAlwaysBurned() || !isInWaterRainOrBubble) {
                if(level().canSeeSky(BlockPos.containing(getX(), getEyeY(), getZ()))) {
                    if (fireImmune() || isInWaterRainOrBubble) {
                        hurt(this.damageSources().generic(), 1.0F);
                    } else {
                        setSecondsOnFire(8);
                    }
                }
            }
        }
        super.aiStep();
    }

    @Override
    protected boolean shouldDropLoot() {
        if(monstersburn$isMobForcedBurn)
            return false;
        return super.shouldDropLoot();
    }
}