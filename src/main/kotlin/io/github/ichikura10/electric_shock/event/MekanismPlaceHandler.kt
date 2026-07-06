package io.github.ichikura10.electric_shock.event

import mekanism.common.tile.base.TileEntityMekanism
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.level.BlockEvent

object MekanismPlaceHandler {

    @SubscribeEvent
    fun onBlockPlace(event: BlockEvent.EntityPlaceEvent) {
        val player = event.entity as? Player ?: return

        if (player.level().isClientSide) return

        val hasArmor = player.inventory.armor.any { !it.isEmpty }
        if (!hasArmor) return

        val blockEntity = event.level.getBlockEntity(event.pos)?: return
        if (blockEntity is TileEntityMekanism) {
            event.isCanceled = true
            player.displayClientMessage(
                Component.translatable("message.electric_shock.cannot_place_mekanism_machine"),
                true
            )
        }
    }
}