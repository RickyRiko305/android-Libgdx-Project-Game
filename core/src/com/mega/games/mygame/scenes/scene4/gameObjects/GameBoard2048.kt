package com.mega.games.mygame.scenes.scene4.gameObjects

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.utils.assets

class GameBoard2048(private val di: DI): GameObject() {

    var numberLabel: TextLabel = di.assets.createLabel("", FontSize.F18, Color.BLACK)
    private var rowSize = 0

    private var blocks = ArrayList<ArrayList<Block>>()

    private var gameStart = false

    var isGameOver = false

    init {
        this.addInputListener()
    }

    fun setRowSize(s: Int) {
        rowSize = s
    }

    fun generate() {
        val blockSize = width / rowSize

        for(i in 0 until rowSize){
            blocks.add(ArrayList())

            for(j in 0 until rowSize){
                val b = Block(di, i, j)
                b.setSize(blockSize, blockSize)
                b.setPosition(i * blockSize, j * blockSize)
                addActor(b)

                blocks[i].add(b)
            }
        }
        addNumbers()
    }

    private fun addNumbers() {
        var temp = 1
        var temp1 = 2
//        for(i in 0 until rowSize){
//            for(j in 0 until rowSize){
//                blocks[i][j].addNumber(temp++)
//            }
//        }
        if (!gameStart){
            while (temp1 > 0){
                val r = MathUtils.random(rowSize-1)
                val c = MathUtils.random(rowSize-1)

                if(!blocks[r][c].isBlock){

                    if (temp1 > 1){
                        blocks[r][c].addNumber(2)
                    }
                    else{
                        blocks[r][c].addNumber(MathUtils.random(1,MathUtils.random(1,2)) * 2)
                    }

                    temp1 -= 1

                    blocks[r][c].isBlock = true
                }
            }
            gameStart = true
        }
        else{
            if (!isGameOver){
                ischeck()
            }
            while (temp > 0 && !isGameOver){
                val r = MathUtils.random(rowSize-1)
                val c = MathUtils.random(rowSize-1)

                if(!blocks[r][c].isBlock){
                    blocks[r][c].addNumber(MathUtils.random(1,MathUtils.random(1,2)) * 2)

                    temp = 0
                    blocks[r][c].isBlock = true
                }
            }
        }
        //blocks[0][2].isBlock = true
    }

    // Directions value
    // 0 -> down
    // 1 -> left
    // 2 -> up
    // 3 -> right
    private fun move(direction: Int) {
        var isOver = false

        if (direction.equals(0)){ //--> down
            for (i in 0 until rowSize) {
                for (j in 1 until rowSize) {
                    var nextPos = j

                    if (!blocks[i][nextPos].isBlock){ nextPos = 0}
                    while (nextPos > 0){
                        //blocks[i][nextPos].debug = true
                        if (!blocks[i][nextPos-1].isBlock){ blocks[i][nextPos].debug = true
                            blocks[i][nextPos-1].addNumber(blocks[i][nextPos].numberAllocated)
                            blocks[i][nextPos-1].isBlock = true
                            blocks[i][nextPos].addNumber(0)
                            blocks[i][nextPos].isBlock = false
                        }
                        else{
                            if(blocks[i][nextPos].numberAllocated == blocks[i][nextPos - 1].numberAllocated){
                                blocks[i][nextPos].addNumber(0)
                                blocks[i][nextPos].isBlock = false
                                blocks[i][nextPos - 1].addNumber(blocks[i][nextPos - 1].numberAllocated * 2)
                            }
                            nextPos = 0
                        }

                        nextPos -= 1
                    }
                }
            }
        }
        else if(direction.equals(2)){ //--> up
            for (i in 0 until rowSize) {
                for (j in 1 until rowSize) {
                    var nextPos = rowSize - 1 - j

                    if (!blocks[i][nextPos].isBlock){ nextPos = rowSize}
                    while (nextPos < (rowSize - 1) && nextPos >= 0){
                        //blocks[i][nextPos].debug = true
                        if (!blocks[i][nextPos + 1].isBlock){ blocks[i][nextPos].debug = true//println(nextPos)//
                            blocks[i][nextPos + 1].addNumber(blocks[i][nextPos].numberAllocated)
                            blocks[i][nextPos + 1].isBlock = true
                            blocks[i][nextPos].addNumber(0)
                            blocks[i][nextPos].isBlock = false
                        }
                        else{
                            if(blocks[i][nextPos].numberAllocated == blocks[i][nextPos + 1].numberAllocated){
                                blocks[i][nextPos].addNumber(0)
                                blocks[i][nextPos].isBlock = false
                                blocks[i][nextPos + 1].addNumber(blocks[i][nextPos + 1].numberAllocated * 2)
                            }
                            nextPos = rowSize - 1
                        }

                        nextPos += 1
                    }
                }
            }
        }
        else if(direction.equals(1)){ //--> left
            for (i in 0 until rowSize) {
                for (j in 1 until rowSize) {
                    var nextPos = j

                    if (!blocks[nextPos][i].isBlock){ nextPos = 0}
                    while (nextPos > 0){
                        //blocks[i][nextPos].debug = true
                        if (!blocks[nextPos-1][i].isBlock){ blocks[nextPos][i].debug = true
                            blocks[nextPos-1][i].addNumber(blocks[nextPos][i].numberAllocated)
                            blocks[nextPos -1][i].isBlock = true
                            blocks[nextPos][i].addNumber(0)
                            blocks[nextPos][i].isBlock = false
                        }
                        else{
                            if(blocks[nextPos][i].numberAllocated == blocks[nextPos - 1][i].numberAllocated){
                                blocks[nextPos][i].addNumber(0)
                                blocks[nextPos][i].isBlock = false
                                blocks[nextPos - 1][i].addNumber(blocks[nextPos - 1][i].numberAllocated * 2)
                            }
                            nextPos = 0
                        }

                        nextPos -= 1
                    }

                }
            }
        }
        else if(direction.equals(3)){ //--> right
            for (i in 0 until rowSize) {
                for (j in 1 until rowSize) {
                    var nextPos = rowSize - 1 - j
                    if (!blocks[nextPos][i].isBlock){ nextPos = rowSize-1}
                    while (nextPos < (rowSize - 1) && nextPos >= 0){
                        //blocks[i][nextPos].debug = true
                        if (!blocks[nextPos + 1][i].isBlock){ blocks[nextPos][i].debug = true
                            blocks[nextPos + 1][i].addNumber(blocks[nextPos][i].numberAllocated)
                            blocks[nextPos + 1][i].isBlock = true
                            blocks[nextPos][i].addNumber(0)
                            blocks[nextPos][i].isBlock = false
                        }
                        else{
                            if(blocks[nextPos][i].numberAllocated == blocks[nextPos + 1][i].numberAllocated){
                                blocks[nextPos][i].addNumber(0)
                                blocks[nextPos][i].isBlock = false
                                blocks[nextPos + 1][i].addNumber(blocks[nextPos + 1][i].numberAllocated * 2)
                            }
                            nextPos = rowSize -1
                        }

                        nextPos += 1
                    }
                }
            }
        }
        addNumbers()
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.UP -> {
                move(2)
                //blocks[1][0].addNumber(2)
            }
            Input.Keys.LEFT -> {
                move(1)
            }
            Input.Keys.RIGHT -> {
                move(3)
            }
            Input.Keys.DOWN -> {
                move(0)
            }
        }
        return super.keyDown(keycode)
    }

    fun ischeck(): Boolean{
        var isOver = true
        for (i in 0 until rowSize) {
            for (j in 0 until rowSize) {
                if (!blocks[i][j].isBlock){
                    isOver = false
                }
            }
        }
        if(isOver){
            isGameOver = true
        }

        return isOver
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = Color(0.9f, 0.9f, 0.9f, 1f)
        batch.draw(di.assets.rect, x, y, width, height)
        batch.color = Color.WHITE

        super.draw(batch, parentAlpha)
    }

}