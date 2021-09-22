package io.ejekta.kambrik.gui

import io.ejekta.kambrik.KambrikHandledScreen
import io.ejekta.kambrik.KambrikScreenCommon
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity


class KGui(
    val screen: Screen,
    private val coordFunc: () -> Pair<Int, Int>,
    var x: Int = 0,
    var y: Int = 0,
    private val func: KGuiDsl.() -> Unit = {}
) {

    val logic: KambrikScreenCommon
        get() = screen as KambrikScreenCommon

    val entityRenderCache = mutableMapOf<EntityType<*>, LivingEntity>()

    fun draw(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float? = null) {
        logic.boundsStack.clear()
        logic.areaClickStack.clear()
        val dsl = KGuiDsl(this, matrices, mouseX, mouseY, delta).draw(func)
    }

    fun absX(relX: Int = 0) = x + coordFunc().first + relX

    fun absY(relY: Int = 0) = y + coordFunc().second + relY

}