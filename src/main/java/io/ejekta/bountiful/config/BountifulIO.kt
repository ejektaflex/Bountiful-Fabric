package io.ejekta.bountiful.config

import io.ejekta.bountiful.Bountiful
import io.ejekta.bountiful.content.BountifulContent
import io.ejekta.bountiful.data.Decree
import io.ejekta.bountiful.data.Pool
import io.ejekta.kambrik.Kambrik
import io.ejekta.kambrik.api.file.KambrikConfigFile
import io.ejekta.kambrik.api.file.KambrikParseFailMode
import io.ejekta.kambrik.ext.ensured
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager

object BountifulIO : SimpleSynchronousResourceReloadListener {

    var configData = BountifulConfigData()

    private val rootFolder = Kambrik.File.getBaseFolder(Bountiful.ID)

    private val poolConfigs = rootFolder.ensured("bounty_pools")
    private val decreeConfigs = rootFolder.ensured("bounty_decrees")

    private val configFile = KambrikConfigFile(rootFolder, "bountiful.json", JsonFormats.DataPack, KambrikParseFailMode.LEAVE, BountifulConfigData.serializer()) { BountifulConfigData() }

    fun getPoolFile(poolName: String): KambrikConfigFile<Pool> {
        return KambrikConfigFile(
            poolConfigs,
            "$poolName.json", JsonFormats.Hand, KambrikParseFailMode.LEAVE, Pool.serializer()) {
            Pool().apply { setup(poolName) }
        }
    }

    fun saveConfig() {
        configFile.write(configData)
    }

    fun loadConfig() {
        configData = configFile.read()
    }

    private val contentLoaders = listOf(
        ResourceLoadStrategy("Pool Loader", "bounty_pools", poolConfigs, Pool.serializer(), BountifulContent.Pools),
        ResourceLoadStrategy("Decree Loader", "bounty_decrees", decreeConfigs, Decree.serializer(), BountifulContent.Decrees)
    )

    override fun apply(resourceManager: ResourceManager) {
        contentLoaders.forEach {
            it.clearDestination()
            it.loadResources(resourceManager)
            it.loadFiles()
        }
    }

    override fun getFabricId() = Bountiful.id("reload_listener")
}
