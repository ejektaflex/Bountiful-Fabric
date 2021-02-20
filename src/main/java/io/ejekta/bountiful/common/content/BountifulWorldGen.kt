package io.ejekta.bountiful.common.content

import io.ejekta.bountiful.common.mixin.StructurePoolAccessor
import net.minecraft.server.MinecraftServer
import net.minecraft.structure.pool.StructurePool
import net.minecraft.structure.pool.StructurePoolElement
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object BountifulWorldGen {

    fun registerJigsaw(server: MinecraftServer, nbtLocation: Identifier, poolLocation: Identifier, weight: Int = 10_000) {

        val pool = server.registryManager.get(Registry.TEMPLATE_POOL_WORLDGEN).entries
            .find { it.key.value.toString() == poolLocation.toString() }?.value ?: throw Exception("Cannot add to '$poolLocation' as it cannot be found!")

        val pieceList = (pool as StructurePoolAccessor).bo_getStructureElements()

        val piece = StructurePoolElement.method_30434(nbtLocation.toString()).apply(StructurePool.Projection.RIGID)

        repeat(weight) {
            pieceList.add(piece)
        }

    }

}