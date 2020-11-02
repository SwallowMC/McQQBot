package com.mczhifu.qqbot

import com.mczhifu.mczf.pack.PlayerPack
import com.mczhifu.mczf.placeholder.PlaceholderAPI

object PlaceholderUtil{
    fun placeholder(_str:String): String {
        val index = ArrayList<Int>();
        var str = _str
        var num = 0;
        str.forEach {
            if ('%' == it ) index.add(num)
            num++
        }
        num = if (index.size % 2 > 0) index.size - 1 else index.size
        for (i in num - 1 downTo 1 step 2){
            str = replace(index[i-1] + 1, index[i], str)
        }
        return str
    }

    private fun replace(start:Int, end:Int, str: String): String{
        var identifier = str.substring(start, end)
        if (identifier.startsWith("mczf_", true)){
            identifier = PlaceholderAPI.handlePlaceholder(PlayerPack(Any()), identifier.substring(5))
        }else{
            return str
        }
        var str1 = str.substring(0, start - 1)
        var str2 = str.substring(end + 1, str.length)
        return str1 + identifier + str2
    }
}