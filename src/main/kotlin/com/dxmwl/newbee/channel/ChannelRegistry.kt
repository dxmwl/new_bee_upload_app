package com.dxmwl.newbee.channel

import com.dxmwl.newbee.BuildConfig
import com.dxmwl.newbee.channel.honor.HonorChannelTask
import com.dxmwl.newbee.channel.huawei.HuaweiChannelTask
import com.dxmwl.newbee.channel.mi.MiChannelTask
import com.dxmwl.newbee.channel.oppo.OPPOChannelTask
import com.dxmwl.newbee.channel.vivo.VIVOChannelTask

private const val DEBUG_TASK = false

object ChannelRegistry {

    private val realChannels: List<ChannelTask> = listOf(
        HuaweiChannelTask(),
        MiChannelTask(),
        OPPOChannelTask(),
        VIVOChannelTask(),
        HonorChannelTask()
    )

    private val mockChannels: List<ChannelTask> = listOf(
        MockChannelTask("华为", "HUAWEI"),
        MockChannelTask("小米", "MI"),
        MockChannelTask("OPPO", "OPPO"),
        MockChannelTask("VIVO", "VIVO"),
    )

    val channels: List<ChannelTask> = if (DEBUG_TASK && BuildConfig.debug) mockChannels else realChannels


    fun getChannel(name: String): ChannelTask? {
        return channels.firstOrNull { it.channelName == name }
    }
}