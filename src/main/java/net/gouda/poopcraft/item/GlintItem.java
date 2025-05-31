package net.gouda.poopcraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GlintItem extends Item {
    public GlintItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(
                null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL,
                0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        );

        if (!world.isClient) {
            SnowballEntity poopEntity = new SnowballEntity(world, user);
            poopEntity.setItem(itemStack);
//            poopEntity.setOnFire(true);
            poopEntity.setOnFireFor(10);
            Vec3d direction = user.getRotationVec(1.0f);
            poopEntity.setVelocity(direction.x * 1.5, direction.y * 1.5, direction.z * 1.5);
            world.spawnEntity(poopEntity);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }


    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
