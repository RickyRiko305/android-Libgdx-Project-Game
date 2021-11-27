package com.mega.games.mygame.scenes.scene5.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.mygame.utils.assets
import java.util.*

class Snake (private val di: DI, si:Float): GameObject() {

    private var snakeBody = LinkedList<BlockDemo>()

    private lateinit var head: BlockDemo
    private lateinit var tail: BlockDemo

    var blockSize = si//width / 10

    init {
        //snakeBody.clear()

        for(i in 0 until 3){
            var body = BlockDemo(di, i , 0)
            body.setSize(blockSize, blockSize)
            body.setPosition((2-i) * blockSize, blockSize * 0)
            body.isSnake = true
            addActor(body)

            snakeBody.push(body)
        }

//        head = snakeBody.first
//        tail = snakeBody.last

        //setPosition( x + blocksize, of )
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        //draw a black bg
//        batch.color = Color.BLACK
        //batch.draw(di.assets.rect, x,y,width, height)
        //reset batch color to remove tint
//        batch.color = Color.BLACK

        //super is used to Draw children of this container (like stars here)
        //super.draw(batch, parentAlpha)

        batch.color = Color(0.9f, 0.9f, 0.9f, 1f)
        batch.draw(di.assets.rect, x, y, width, height)
        batch.color = Color.WHITE

        super.draw(batch, parentAlpha)
    }


}