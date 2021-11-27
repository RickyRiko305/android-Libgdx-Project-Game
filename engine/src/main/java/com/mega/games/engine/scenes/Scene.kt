package com.mega.games.engine.scenes

import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.dependency.renderManager
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener

abstract class Scene(private val di: DI) {
    //Game objects are updated and drawn in the same order as they are added
    protected val stage get() = di.renderManager.stage

    fun addObjToScene(gameObject: GameObject) {
        di.renderManager.stage.addActor(gameObject)
    }

    abstract fun act(dt:Float)
}