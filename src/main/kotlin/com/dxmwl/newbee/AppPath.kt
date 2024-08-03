package com.dxmwl.newbee

import java.io.File
import java.nio.file.Files

object AppPath {

    fun getRootDir(): File {
        val homeDir = File(requireNotNull(System.getProperty("user.home")))
        val rootDir = File(homeDir, ".XiaoZhuan")
        return if (BuildConfig.debug) {
            File(rootDir, "debug")
        } else {
            rootDir
        }
    }

    fun getLogDir(): File {
        return File(getRootDir(), "log")
    }

    fun getApkDir(): File {
        return File(getRootDir(), "apk")
    }

    /**
     * 获取此目录下的Apk文件
     * 会递归遍历此目录，获取目录下前20个Apk，然后按修改时间降序返回
     */
    fun listApk(dir: File): List<File> {
        return dir.walkBottomUp()
            .maxDepth(3)
            .toList()
            .filter { it.name.endsWith(".apk", true) }
            .sortedByDescending { Files.getLastModifiedTime(it.toPath()) }
            .take(20)
    }
}