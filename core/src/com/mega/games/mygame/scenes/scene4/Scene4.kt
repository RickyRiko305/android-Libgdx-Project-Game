package com.mega.games.mygame.scenes.scene4

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.scenes.Scene
import com.mega.games.engine.values.Values
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.scenes.scene3.gameObjects.GameBoard
import com.mega.games.mygame.scenes.scene4.gameObjects.GameBoard2048
import com.mega.games.mygame.utils.assets

class Scene4(private val di: DI) : Scene(di){

    private val screenCenter = Vector2(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT/2f)

    private val assets = di.assets

    lateinit var board:GameBoard2048

    init {
        addBoard()

        addTitle()

    }

    private fun addTitle() {
        val title = assets.createLabel("2048", size = FontSize.F20, color = Color.BLACK)
        title.setPosition(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT, Align.top)
        addObjToScene(title)
    }

    private fun addGameOver() {
        val gameOver = assets.createLabel("Game Over", size = FontSize.F14, color = Color.BLACK)
        gameOver.setPosition(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT/2, Align.center)
        addObjToScene(gameOver)
    }

    private fun addBoard() {
        board = GameBoard2048(di)
        val boardSize = Values.VIRTUAL_HEIGHT * 0.8f
        board.setSize(boardSize, boardSize)
        board.setRowSize(4)

        board.setPosition(screenCenter.x, screenCenter.y, Align.center)

        board.generate()

        addObjToScene(board)

        stage.keyboardFocus = board
    }

    override fun act(dt: Float) {

        if (board.isGameOver){
            addGameOver()
        }

    }

}

/**
 * Complete below tasks. you can make your own decisions and choices wherever required
 *
 * The aim of this scene is to make a game of 2048. You can reference this link : https://2048game.com/
 *
 * Try to make the game as similar to the link provided as possible. Including colors, animations etc
 * You can use the rect texture for making all the squares
 */