package com.mega.games.mygame.scenes.scene2.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.values.Values
import com.mega.games.mygame.utils.assets

class Nebula (private val di: DI): GameObject() {
    private val myTex = di.assets.nebula

    private var scrollSpeed = -2f       //move of left direction

    init{
        color = Color(1f,1f,1f,0.6f)

        val scale = Values.VIRTUAL_WIDTH / myTex.width
        setSize(myTex.width.toFloat() * scale, myTex.height.toFloat() * scale)
    }

    override fun act(dt: Float) {
        //since only moving in x, the y value of moveBy is 0
        this.moveBy(scrollSpeed * 0.5f, 0f)
//        if(x > width){
//            x-=width
//        }
        if(x < 0){         //reset the
            x+=width
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = color
        batch.draw(myTex,x,y,width, height)
        batch.draw(myTex,x-width,y,width, height)
        batch.color = Color.WHITE
    }
}