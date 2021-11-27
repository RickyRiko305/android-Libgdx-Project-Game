package com.mega.games.mygame

import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.EngineGame
import com.mega.games.engine.dependency.assetManager
import com.mega.games.engine.scenes.Scene
import com.mega.games.engine.values.Values
import com.mega.games.mygame.asset.Assets
import com.mega.games.mygame.scenes.scene1.Scene1
import com.mega.games.mygame.scenes.scene2.Scene2
import com.mega.games.mygame.scenes.scene3.Scene3
import com.mega.games.mygame.scenes.scene4.Scene4
import com.mega.games.mygame.scenes.scene5.Scene5
import com.mega.games.mygame.scenes.scene6.Scene6
import com.mega.games.mygame.scenes.scene7.Scene7

class MyGame(
    resolver: FileHandleResolver
) : EngineGame(resolver, defaultAspectRatio = Pair(16f, 9f)) {
    private lateinit var scene:Scene

    init{
        //todo.note - Change screen scale below
        Values.screenFactor = 40f;
    }

    //This is called at the beginning. We load the assets first
    override fun onCreate() {
        //add assets to DI(Dependency Injector)
        di.add(Assets::class.java, Assets(di.assetManager))
    }

    //At this point assets are loaded. You should create your game here
    override fun onLoaded() {
        //todo.note - Change the scene below
//        scene = Scene1(di)
//        scene = Scene2(di)
//        scene = Scene3(di)
//        scene = Scene4(di)
//        scene = Scene5(di)
//        scene = Scene6(di)
        scene = Scene7(di)

        //add scene to DI
        di.add(Scene::class.java,scene)
    }

    //update is called once every frame
    override fun update(dt: Float) {
        scene.act(dt)
    }

    override fun draw(batch: Batch) {

    }
}