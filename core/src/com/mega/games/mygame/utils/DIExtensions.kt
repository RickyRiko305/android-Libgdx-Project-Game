package com.mega.games.mygame.utils

import com.mega.games.engine.dependency.DI
import com.mega.games.engine.scenes.Scene
import com.mega.games.mygame.asset.Assets

//todo.note - Add shared objects to dependency injector so that a reference can be passed to different gameObjects
val DI.scene get() = this[Scene::class.java]
val DI.assets get() = this[Assets::class.java]
