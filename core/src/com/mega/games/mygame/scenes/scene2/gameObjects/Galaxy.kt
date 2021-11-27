package com.mega.games.mygame.scenes.scene2.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.values.Values
import com.mega.games.mygame.utils.assets

class Galaxy (private val di: DI): GameObject() {
    private val myTex = di.assets.galaxies[MathUtils.random(0, di.assets.galaxies.size - 1)]

    private var scrollSpeed = -2f

    init {
        setSize(myTex.width.toFloat(), myTex.height.toFloat())

        rotation = MathUtils.random(360f)
    }

    override fun act(dt: Float) {
        //since only moving in x, the y value of moveBy is 0
        this.moveBy(scrollSpeed * 0.5f, 0f)

        if(x < 0){         //reset the
            x+= Values.VIRTUAL_WIDTH
        }
    }


    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = color
        batch.draw(myTex,x,y,Values.VIRTUAL_WIDTH/2, Values.VIRTUAL_HEIGHT/2)
        batch.draw(myTex,x-Values.VIRTUAL_WIDTH,y,Values.VIRTUAL_WIDTH/2, Values.VIRTUAL_HEIGHT/2)
        batch.color = Color.WHITE
    }
}