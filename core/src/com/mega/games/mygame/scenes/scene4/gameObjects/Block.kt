package com.mega.games.mygame.scenes.scene4.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.utils.assets

class Block(private val di: DI, i:Int, j:Int):GameObject() {
    var numberLabel: TextLabel = di.assets.createLabel("", FontSize.F18, Color.BLACK)

    var isBlock = false

    var incides = Pair(i,j)

    var numberAllocated = 0

    private var blockColor = Color(0.95f, 0.95f, 0.95f, 1f)

    init {
        addActor(numberLabel)
    }

    fun addNumber(num: Int) {
        //isEmpty = false
        if (num.equals(0)){
            numberLabel.setText("")
        }
        else {
            numberLabel.setText(num.toString())
        }
        numberLabel.setSize(numberLabel.reqWidth, numberLabel.reqHeight)
        numberLabel.setPosition(width/2, height/2, Align.center)

        setColor(num)
    }

    private fun setColor (num: Int) {
        numberAllocated = num

        when(MathUtils.log2(num.toFloat()).toInt()) {
            0 -> blockColor = Color(0.95f, 0.95f, 0.95f, 1f)
            1 -> blockColor = Color.LIGHT_GRAY
            2 -> blockColor = Color.GOLDENROD
            3 -> blockColor = Color.ORANGE
            4 -> blockColor = Color.GOLD
            5 -> blockColor = Color.OLIVE
            6 -> blockColor = Color.LIME
            7 -> blockColor = Color.GREEN
            8 -> blockColor = Color.RED
            else -> {
                blockColor = Color(0.95f, 0.95f, 0.95f, 1f)//Color.BLUE
            }

        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val cellMargins = width * 0.05f

        //if (isBlock) {
            batch.color = blockColor//(0.95f, 0.95f, 0.95f, 1f)
        //}


        batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)

        batch.color = Color.WHITE

        //if(!isBlock) {
            super.draw(batch, parentAlpha)
            //batch.color = Color(0.95f, 0.95f, 0.95f, 1f)
        //}

    }
}