package com.mega.games.mygame.scenes.scene1.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.mygame.utils.assets

class Ground(private val di: DI): GameObject() {
    //fetch a reference to a rectangle texture for my ball object
    private val tex = di.assets.rect

    init{
        color.set(Color.BROWN)
    }

    /**
     * Draw is called after act each frame
     * Takes a batch for drawing textures to screen
     * can be used to refresh the screen by drawing objects to their new location calculated in act
     */
    override fun draw(batch: Batch, parentAlpha: Float) {
        //change the batch color to balls color
        batch.color = color

        //draw circle tex at ball's position and size
        batch.draw(tex, x, y, width, height)

        //ALWAYS revert batch color back to white
        batch.color = Color.WHITE
    }
}