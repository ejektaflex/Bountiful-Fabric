package io.ejekta.bountiful.bounty.types.builtin

import io.ejekta.bountiful.bounty.BountyDataEntry
import io.ejekta.bountiful.bounty.types.IBountyObjective
import io.ejekta.bountiful.bounty.types.IBountyType
import io.ejekta.bountiful.bounty.types.Progress
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.MutableText
import net.minecraft.text.Text


class BountyTypeCriteria : IBountyObjective {

    override fun verifyValidity(entry: BountyDataEntry, player: PlayerEntity): MutableText? {
        return null
    }

    override fun textSummary(entry: BountyDataEntry, isObj: Boolean, player: PlayerEntity): MutableText {
        return Text.literal(entry.criteria?.description ?: "NO TRIGGER DESCRIPTION")
    }

    override fun textBoard(entry: BountyDataEntry, player: PlayerEntity): List<Text> {
        return listOf(Text.literal(entry.criteria?.description ?: "BEEP BOOP"))
    }

    override fun getProgress(entry: BountyDataEntry, player: PlayerEntity): Progress {
        return Progress(entry.current, entry.amount)
    }

    override fun tryFinishObjective(entry: BountyDataEntry, player: PlayerEntity): Boolean {
        return entry.current >= entry.amount
    }

}