package com.mczhifu.qqbot

class KryptonGoldConfig (){
    var kryptonGoldConfig = configManager.allConfig["config"]
        ?.getConfiguration("qq-group")
        ?.getConfiguration("krypton-gold")

    var usage = kryptonGoldConfig?.getString("usage")
    var identifier = kryptonGoldConfig?.getStringList("identifier")
    var qqidentifier = kryptonGoldConfig?.getStringList("pay-type.qq")
    var wxidentifier = kryptonGoldConfig?.getStringList("pay-type.wx")
    var zfbidentifier = kryptonGoldConfig?.getStringList("pay-type.zfb")

    var enable = kryptonGoldConfig?.getBoolean("enable")
}