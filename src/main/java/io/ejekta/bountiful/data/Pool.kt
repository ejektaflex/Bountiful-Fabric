package io.ejekta.bountiful.data

import io.ejekta.bountiful.content.BountifulContent
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Pool(
    @Transient override var id: String = "DEFAULT_POOL",
    val replace: Boolean = false,
    override val requires: MutableList<String> = mutableListOf(),
    val content: MutableList<PoolEntry> = mutableListOf()
) : IMerge<Pool> {

    fun setup(newId: String) {
        id = newId
        // Do weight normalization
        val overallMult = content.size
        content.takeIf { it.isNotEmpty() }?.forEach {
            it.weightMult /= overallMult
            it.sources.add(id)
        }
    }

    operator fun iterator() = content.iterator()

    val usedInDecrees: List<Decree>
        get() = BountifulContent.Decrees.filter { this.id in it.allPoolIds }

    override fun merge(other: Pool) {
        when (other.replace) {
            true -> {
                content.clear()
                content.addAll(other.content)
            }
            false -> content.addAll(other.content)
        }
    }

    override fun merged(other: Pool): Pool {
        return when (other.replace) {
            true -> Pool(id, content = other.content)
            else -> Pool(id, content = (content + other.content).toMutableList())
        }
    }

}