package net.gouda;

import net.fabricmc.api.ModInitializer;

import net.gouda.item.MyModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PoopCraft implements ModInitializer {
	public static final String MOD_ID = "poopcraft";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final Map<UUID, Boolean> wasSneaking = new HashMap<>();


	@Override
	public void onInitialize() {
		MyModItems.register();
		ServerTickEvents.START_WORLD_TICK.register(world -> {
			for (PlayerEntity player : world.getPlayers()) {
				UUID id = player.getUuid();
				boolean isSneaking = player.isSneaking();
				if (isSneaking && !wasSneaking.getOrDefault(id, false)) {
					dropItem(player);
				}

				wasSneaking.put(id, isSneaking);
			}
		});
	}

	private static void dropItem(PlayerEntity player){
		ItemStack stack = new ItemStack(MyModItems.POOP);
		Vec3d pos = player.getPos().add(0, 0.5, 0);
		Vec3d look = player.getRotationVector().multiply(0.5);

		ItemEntity item = new ItemEntity(
				player.getWorld(),
				pos.x + look.x,
				pos.y,
				pos.z + look.z,
				stack
		);
		item.setVelocity(0, 0.1, 0);

		player.getWorld().spawnEntity(item);
	}
}