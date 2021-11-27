package com.mega.games.mygame.scenes.scene2.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.values.Values
import com.mega.games.mygame.utils.assets

class StarryBG (private val di: DI): GameObject() {
    init{
        setSize(Values.VIRTUAL_WIDTH, Values.VIRTUAL_HEIGHT)

        addNebula()
        addStars()
        addGalaxy()
    }

    private fun addNebula() {
        val nebula = Nebula(di)
        nebula.setPosition(width/2, height/2, Align.center)
        addActor(nebula)
    }

    private fun addStars() {
        val numStars = 100
        for(i in 0 until numStars){
            val s = Star(di)
            s.setPosition(MathUtils.random(width), MathUtils.random(height))

            //todo.note - Every gameObject is a container and only needs to override draw if directly drawing textures itself using batch
            addActor(s)
        }
    }

    private fun addGalaxy(){
        val numGalaxy = 5
        for(i in 0 until numGalaxy){
            val g = Galaxy(di)
            g.setPosition(MathUtils.random(width), MathUtils.random(height))
            addActor(g)
        }
    }


    override fun draw(batch: Batch, parentAlpha: Float) {
        //draw a black bg
        batch.color = Color.BLACK
        batch.draw(di.assets.rect, x,y,width, height)
        //reset batch color to remove tint
        batch.color = Color.WHITE

        //super is used to Draw children of this container (like stars here)
        super.draw(batch, parentAlpha)
    }
}