package com.mega.games.mygame.scenes.scene3.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.scenes.scene3.Scene3
import com.mega.games.mygame.utils.assets
import com.mega.games.mygame.utils.scene
import java.awt.Button

class Cell(private val di: DI, i:Int, j:Int): GameObject() {
    var nearbyMinesLabel:TextLabel = di.assets.createLabel("", FontSize.F12, Color.BLACK)

    var hasMine = false
    var isRevealed = false
    var isFlaged = false
    var isEmpty = true

    var indices = Pair(i,j)         // i and j is changed

    init {
        addActor(nearbyMinesLabel)
        this.addInputListener()
    }

    //adds a mine to this cell
    fun addMine() {
        isEmpty = false
        hasMine = true
        nearbyMinesLabel.setText("")
    }

    //adds number specifying surrounding mines
    fun addNumber(num: Int) {
        isEmpty = false
        nearbyMinesLabel.setText(num.toString())
        nearbyMinesLabel.setSize(nearbyMinesLabel.reqWidth, nearbyMinesLabel.reqHeight)
        nearbyMinesLabel.setPosition(width/2, height/2, Align.center)
    }

    fun reveal() {
        isRevealed = true
        isFlaged = false
    }

    //touchDown is used to detect touch screen input or left mouse button
    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            //System.out.println("Mouse clicked!");
            val scene = di.scene
            if(scene is Scene3){
                scene.board.cellClicked(indices.first, indices.second)
            }
        }

        return super.touchDown(x, y, pointer, button)
    }

//    override fun leftClicked(x: Float, y: Float) {
//        val scene = di.scene
//        if(scene is Scene3){
//            scene.board.cellClicked(indices.first, indices.second)
//        }
//        return super.leftClicked(x, y)
//    }

    override fun rightClicked(x: Float, y: Float) {
        //Todo: fill this to add a flag
        val scene = di.scene
        if(scene is Scene3){
            scene.board.cellFlaged(indices.first, indices.second)
        }
        return super.rightClicked(x, y)
    }

    fun redFlag(){
        isFlaged = true;
        isRevealed = false
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val cellMargins = width * 0.05f

        //draw cell BG
        if(!isRevealed) {
            batch.color = Color(0.95f, 0.95f, 0.95f, 1f)
        }
        batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        batch.color = Color.WHITE

        //draw cell contents if revealed
        if(isRevealed) {
            if (hasMine) {
                batch.draw(di.assets.mine, x + cellMargins, y + cellMargins, width - 2 * cellMargins, height - 2 * cellMargins)
            }

            //super.draw will draw the label for number
            super.draw(batch, parentAlpha)
        }

        if(isFlaged) {
            batch.draw(di.assets.flag, x + cellMargins, y + cellMargins, width - 2 * cellMargins, height - 2 * cellMargins)
        }
    }
}