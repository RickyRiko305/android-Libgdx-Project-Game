package com.mega.games.mygame.scenes.scene7.gameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.mygame.scenes.scene6.Scene6
import com.mega.games.mygame.scenes.scene7.Scene7
import com.mega.games.mygame.utils.assets
import com.mega.games.mygame.utils.scene

class colorCell(private val di: DI, i:Int, j:Int): GameObject() {

    var indices = Pair(i,j)

    var numberAllocated = 0

    var blockColor = Color(0.95f, 0.95f, 0.95f, 1f)

    var isRed = false
    private var elapsedTime = 0f

    init {
        this.addInputListener()
    }

    fun setColor (num: Int) {
        numberAllocated = num

        when(num) {
            0 -> blockColor = Color(0.95f, 0.95f, 0.95f, 1f)
            1 -> blockColor = Color.ORANGE
            2 -> blockColor = Color.GREEN
            3 -> blockColor = Color.BLUE
            4 -> blockColor = Color.LIGHT_GRAY
            5 -> blockColor = Color.OLIVE
            6 -> blockColor = Color.LIME
            7 -> blockColor = Color.BLACK//Color.GOLDENROD
            8 -> blockColor = Color.RED
            else -> {
                blockColor = Color(0.95f, 0.95f, 0.95f, 1f)//Color.BLUE
            }

        }
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            //System.out.println("Mouse Up");
            val scene = di.scene
            if(scene is Scene7){
                scene.board.cellClicked(indices.first, indices.second, x, y)
            }
        }
        System.out.println("Mouse down 1");

        return true//super.touchDown(x, y, pointer, button)
    }

    override fun touchUp(x: Float, y: Float, pointer: Int, button: Int) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            System.out.println("Mouse Up ");
//            val scene = di.scene
//            if(scene is Scene7){
//                scene.board.cellDirection( x, y)
//            }
        }

        val scene = di.scene
        if(scene is Scene7){
            scene.board.cellDirection( x, y)
        }

        System.out.println("Mouse Up 1");
        super.touchUp(x, y, pointer, button)
    }

    override fun act(dt: Float) {

//        System.out.println("ball")
//
//        if (isRed){
//            System.out.println("Red")
//            blockColor = Color.RED
//            elapsedTime += dt
//
//            if(elapsedTime > 3f){
//                System.out.print("Red")
//                //elapsedTime -= 0.5f
//                elapsedTime = 0f
//                isRed = false
//                setColor(numberAllocated)
//            }
//
////            if(isRed){
////                color.lerp(1f,0f,0f,1f,0.1f)
////            }
////            else{
////                color.lerp(0f,0f,1f,1f,0.1f)
////            }
//        }


    }


    override fun draw(batch: Batch, parentAlpha: Float) {

        val cellMargins = width * 0.05f

        //batch.color = Color(0.95f, 0.95f, 0.95f, 1f)


        // batch.draw(di.assets.rect,x+cellMargins,y+cellMargins,width-2*cellMargins,height-2*cellMargins)
        if (numberAllocated > 0){
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