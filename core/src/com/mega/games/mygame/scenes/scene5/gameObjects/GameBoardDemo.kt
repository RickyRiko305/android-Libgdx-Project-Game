package com.mega.games.mygame.scenes.scene5.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.mygame.scenes.scene4.gameObjects.Block
import com.mega.games.mygame.utils.assets
import java.util.*
import kotlin.collections.ArrayList

class GameBoardDemo(private val di: DI): GameObject() {

    private var rowSize = 0
    private var columnSize = 0

    private var blocks = ArrayList<ArrayList<BlockDemo>>()

    private var gameStart = false

    var isGameOver = false

    lateinit var snake:Snake

    private var snakeBody = ArrayList<BlockDemo>()
    private lateinit var head: BlockDemo
    private lateinit var tail: BlockDemo

    private var countMoves = 0

    private var elapsedTime = 0f

    init {
        this.addInputListener()
    }

    fun setRowSize(s: Int) {
        rowSize = s
    }

    fun setColumnSize(s: Int) {
        columnSize = s
    }

    fun generate() {
        val blockSize = width / rowSize

        for(i in 0 until rowSize){
            blocks.add(ArrayList())

            for(j in 0 until columnSize){
                val b = BlockDemo(di, i, j)
                b.setSize(blockSize, blockSize)
                b.setPosition(i * blockSize, j * blockSize)
                //b.isSnake = true
                addActor(b)

//                if(j!=0){
//
//                    blocks[i].add(b)
//                }
                blocks[i].add(b)
            }
        }

        //makeSnake()
    }

    fun makeSnake(){
        var blockSize = width / rowSize
        //snake = Snake(di, blockSize)
        //snakeBody.clear()

        for(i in 0 until 3){
            var body = BlockDemo(di, i , 0)
            body.setSize(blockSize, blockSize)
            body.setPosition((i) * blockSize, blockSize * 0)
            body.isSnake = true
            addActor(body)

            snakeBody.add(body)
        }
//        head = snakeBody.first
//        tail = snakeBody.last
    }

    override fun act(dt: Float) {

        elapsedTime += dt
        if(elapsedTime > 0.5f){
            elapsedTime = 0f
            if(countMoves < 3){
//                for(i in 0 until 2){
////                var nextBody: BlockDemo = snakeBody[i+1]
////                var body: BlockDemo = snakeBody[i]
////                body.setPosition(nextBody.x, nextBody.y)
//                    snakeBody[i].setPosition(snakeBody[i+1].x,snakeBody[i+1].y)
//                    //body =
//                    //snakeBody.
//                }
                snakeBody[0].setPosition(snakeBody[0].x +(width / rowSize),snakeBody[0].y )
                snakeBody[1].setPosition(snakeBody[1].x +(width / rowSize),snakeBody[1].y )
                snakeBody[2].setPosition(snakeBody[2].x +(width / rowSize),snakeBody[2].y )

                countMoves += 1
            }
        }




        //head.setPosition(he)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = Color(0.9f, 0.9f, 0.9f, 1f)
        batch.draw(di.assets.rect, x, y, width, height)
        batch.color = Color.WHITE

        super.draw(batch, parentAlpha)
    }
}