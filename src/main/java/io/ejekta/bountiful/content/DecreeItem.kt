package io.ejekta.bountiful.content

import io.ejekta.bountiful.bounty.DecreeData
import kotlinx.serialization.InternalSerializationApi
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.world.World

class DecreeItem : Item(
    FabricItemSettings()
        .maxCount(1)
        .fireproof()
        .group(ItemGroup.MISC)
) {

    override fun getTranslationKey() = "bountiful.decree"

    override fun getName(stack: ItemStack?): Text {
        return TranslatableText(translationKey).formatted(Formatting.DARK_PURPLE)
    }

    @InternalSerializationApi
    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext?
    ) {
        if (stack != null && world != null) {
            val data = DecreeData[stack].tooltipInfo(world)
            tooltip.addAll(data)
        }
        super.appendTooltip(stack, world, tooltip, context)
    }

    companion object {

        fun create(data: DecreeData? = null): ItemStack {
            return ItemStack(BountifulContent.DECREE_ITEM).apply {
                DecreeData[this] = data ?: DecreeData(
                    mutableListOf(
                        BountifulContent.Decrees.random().id
                    )
                )
            }
        }

        fun create(decId: String): ItemStack {
            return ItemStack(BountifulContent.DECREE_ITEM).apply {
                DecreeData[this] =  DecreeData(
                    mutableListOf(
                        BountifulContent.Decrees.find { it.id == decId }?.id
                    ).filterNotNull().toMutableList()
                )
            }
        }

    }

}