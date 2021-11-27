package com.mega.games.mygame.scenes.scene6

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.scenes.Scene
import com.mega.games.engine.values.Values
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.scenes.scene5.gameObjects.GameBoardDemo
import com.mega.games.mygame.scenes.scene6.gameObjects.GameBoardChain
import com.mega.games.mygame.utils.assets

class Scene6(private val di: DI) : Scene(di) {
    private val screenCenter = Vector2(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT/2f)

    private val assets = di.assets

    lateinit var board: GameBoardChain

    private val size = 8

    val players = 3

    init {
        addTitle()

        addBoard()
    }

    private fun addTitle() {
        val title = assets.createLabel("Chain Reaction", size = FontSize.F20, color = Color.BLACK)
        title.setPosition(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT, Align.top)
        addObjToScene(title)
    }

    fun addGameCompleted(num:Int) {
        val gameCompleted = assets.createLabel("Game Completed: Won By " + num, size = FontSize.F14, color = Color.BLACK)
        gameCompleted.setPosition(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT/2, Align.center)
        addObjToScene(gameCompleted)
    }

    private fun addBoard() {
        board = GameBoardChain(di)
        val boardSizeHeight = Values.VIRTUAL_HEIGHT * 0.8f
        val boardSizeWidth = Values.VIRTUAL_HEIGHT * 0.8f
        board.setSize(boardSizeWidth, boardSizeHeight)
        board.setRowSize(size)
        board.setColumnSize(size)

        board.setPosition(screenCenter.x, screenCenter.y, Align.center)

        board.generate()

        addObjToScene(board)

        stage.keyboardFocus = board

    }

    override fun act(dt: Float) {

    }
}