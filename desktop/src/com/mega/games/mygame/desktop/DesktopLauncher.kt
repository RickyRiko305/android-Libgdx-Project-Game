package com.mega.games.mygame.desktop

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mega.games.engine.values.Values
import com.mega.games.mygame.MyGame

object DesktopLauncher {
    private val cfg by lazy {
        LwjglApplicationConfiguration().apply {
            width = Values.VIRTUAL_WIDTH.toInt()
            height = Values.VIRTUAL_HEIGHT.toInt()

            useHDPI = true

            samples = 2
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        LwjglApplication(MyGame(InternalFileHandleResolver()), cfg)
    }
}