package com.mega.games.mygame.scenes.scene5

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.scenes.Scene
import com.mega.games.engine.values.Values
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.scenes.scene5.gameObjects.GameBoardDemo
import com.mega.games.mygame.utils.assets

class Scene5(private val di: DI) : Scene(di) {
    private val screenCenter = Vector2(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT/2f)

    private val assets = di.assets

    lateinit var board:GameBoardDemo

    init {
        addTitle()

        addBoard()
    }

    private fun addTitle() {
        val title = assets.createLabel("DEMO", size = FontSize.F20, color = Color.BLACK)
        title.setPosition(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT, Align.top)
        addObjToScene(title)
    }

    private fun addBoard() {
        board = GameBoardDemo(di)
        val boardSizeHeight = Values.VIRTUAL_HEIGHT * 0.8f
        val boardSizeWidth = Values.VIRTUAL_HEIGHT * 0.8f
        board.setSize(boardSizeWidth, boardSizeHeight)
        board.setRowSize(10)
        board.setColumnSize(10)

        board.setPosition(screenCenter.x, screenCenter.y, Align.center)

        board.generate()
        board.makeSnake()

        addObjToScene(board)

        stage.keyboardFocus = board
    }



    override fun act(dt: Float) {

    }

}