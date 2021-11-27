package com.mega.games.mygame.scenes.scene5.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.utils.assets

class BlockDemo(private val di: DI, i:Int, j:Int): GameObject() {

    var numberLabel: TextLabel = di.assets.createLabel("", FontSize.F18, Color.BLACK)

    var isBlock = false

    var incides = Pair(i,j)

    var isSnake = false;


    override fun draw(batch: Batch, parentAlpha: Float) {

        val cellMargins = width * 0.05f

        if (isSnake){
            batch.color = Color.GREEN
            batch.draw(di.assets.circle,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        }
        else{
            batch.color = Color(0.95f, 0.95f, 0.95f, 1f)
            batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        }



        //batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)

        batch.color = Color.WHITE
        super.draw(batch, parentAlpha)

    }
}