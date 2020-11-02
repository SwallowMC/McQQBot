package com.mczhifu.qqbot

import com.mczhifu.mczf.config.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * BukkitPlugin
 *
 * @author msg_dw
 */
class BukkitMain : JavaPlugin(){
    /**
     * Plugin Enable
     */
    override fun onEnable() {
        logger.info("${name}正在启动")
        configManager = ConfigManager(dataFolder)
        CommonMain.onStart()
        if (configManager.allConfig["config"]?.getBoolean("qq-group.pay-notify.enable")!!){
            Bukkit.getPluginManager().registerEvents(BukkitListener(), this)
        }
    }

    /**
     * Plugin Disable
     */
    override fun onDisable() {
        logger.info("${name}正在停止")
    }
}
