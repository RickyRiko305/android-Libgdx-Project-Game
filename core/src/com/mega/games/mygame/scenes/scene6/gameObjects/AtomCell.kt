package com.mega.games.mygame.scenes.scene6.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.scenes.scene3.Scene3
import com.mega.games.mygame.scenes.scene6.Scene6
import com.mega.games.mygame.utils.assets
import com.mega.games.mygame.utils.scene

class AtomCell(private val di: DI, i:Int, j:Int): GameObject() {

    var numberLabel: TextLabel = di.assets.createLabel("", FontSize.F18, Color.BLACK)

    private var cellColor = Color(0.95f, 0.95f, 0.95f, 1f)

    var number = 0

    var indices = Pair(i,j)

    var roatationSpeed = 0.3f

    var numberAllocated = 0

    var blockColor = Color(0.95f, 0.95f, 0.95f, 1f)

    init {
        addActor(numberLabel)
        this.addInputListener()
    }

    fun addNumber(num: Int) {
        number += num

        if (number in 1..3){
            numberLabel.setText(number.toString())
            numberLabel.setSize(numberLabel.reqWidth, numberLabel.reqHeight)
            numberLabel.setPosition(width/2, height/2, Align.center)
        }

        if (number == 4){
            number = 0
            numberLabel.setText("")
            numberLabel.setSize(numberLabel.reqWidth, numberLabel.reqHeight)
            numberLabel.setPosition(width/2, height/2, Align.center)
        }

        //setColor(number)
    }


    fun setRotionSpeed(){
        roatationSpeed = (0.3 * number).toFloat()
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            //System.out.println("Mouse clicked!");
            val scene = di.scene
            if(scene is Scene6){
                scene.board.cellClicked(indices.first, indices.second)
            }
        }

        return super.touchDown(x, y, pointer, button)
    }

    fun setColor (num: Int) {
        numberAllocated = num

        when(num) {
            0 -> blockColor = Color(0.95f, 0.95f, 0.95f, 1f)
            1 -> blockColor = Color.RED
            2 -> blockColor = Color.GREEN
            3 -> blockColor = Color.BLUE
            4 -> blockColor = Color.ORANGE
            5 -> blockColor = Color.OLIVE
            6 -> blockColor = Color.LIME
            7 -> blockColor = Color.GOLDENROD
            8 -> blockColor = Color.LIGHT_GRAY
            else -> {
                blockColor = Color(0.95f, 0.95f, 0.95f, 1f)//Color.BLUE
            }

        }
    }

    override fun act(dt: Float) {

    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        val cellMargins = width * 0.05f

        //batch.color = Color(0.95f, 0.95f, 0.95f, 1f)


       // batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        if (number > 0){
            batch.color = blockColor//Color.GREEN
            batch.draw(di.assets.circle,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        }
        else{
            batch.color = Color(0.95f, 0.95f, 0.95f, 1f)
            batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        }

        batch.color = Color.WHITE

        super.draw(batch, parentAlpha)

    }
}