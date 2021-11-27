package com.mega.games.mygame.scenes.scene2.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.mygame.utils.assets

class Star (private val di: DI): GameObject() {
    private val myTex = di.assets.stars[MathUtils.random(0, MathUtils.random(0,di.assets.stars.size - 1))]

    private val meanScale = MathUtils.random( 0.13f)   //0.2 // decrease the star size
    private var sizeMultiplier = meanScale

    private var elapsed = 0f  //MathUtils.random(5f)//
    private var frequency = MathUtils.random(2)

    private var starShade = Color.RED

    init{
        setSize(myTex.width.toFloat(), myTex.height.toFloat())

        //give a random rotation to each star
        rotation = MathUtils.random(360f)
        starColorShader()
    }

    override fun act(dt: Float) {
        elapsed += dt * 360 * 0.5f //MathUtils.random(3f)
        //elapsed += dt * 180 * 0.5f   // takes longer time
        //var t = MathUtils.sinDeg(elapsed)
        //sizeMultiplier = meanScale * (1 + MathUtils.cosDeg(elapsed))
        if (frequency.equals(1))
        {
            sizeMultiplier = meanScale * (1 + MathUtils.sinDeg(elapsed))
        }
        else
        {
            sizeMultiplier = meanScale * (-1 + MathUtils.sinDeg(elapsed))    //can also use cosDeg --> MathUtils.cosDeg(elapsed)
        }
        //sizeMultiplier = meanScale * (MathUtils.random(5f) + MathUtils.sinDeg(elapsed))
    }

    private fun starColorShader(){

        when(MathUtils.random(4)){
            //1 -> starShade = Color.GREEN
            1 -> starShade = Color.RED
            2 -> starShade = Color.WHITE
            3 -> starShade = Color.BLUE
            4 -> starShade = Color.YELLOW
        }

    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val scaledWidth = width * sizeMultiplier
        val scaledHeight = height * sizeMultiplier

        batch.color = starShade//Color.RED//color
        batch.draw(myTex,x - scaledWidth/2,y - scaledHeight/2, scaledWidth, scaledHeight)
        batch.color = Color.WHITE
    }
}