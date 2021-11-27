package com.mega.games.engine.dependency

import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.mega.games.engine.asset.GameAssetManager
import com.mega.games.engine.utils.FileHandleResolverWrapper
import com.mega.games.engine.view.RenderManager

fun DI.setupInfra(resolver: FileHandleResolver) {
    add(RenderManager::class.java, RenderManager())
    add(GameAssetManager::class.java, GameAssetManager(FileHandleResolverWrapper(resolver)))
}