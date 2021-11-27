package com.mega.games.mygame.scenes.scene1.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.values.Values
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.utils.assets

class Ball(private val di: DI): GameObject() {
    //todo.note - references to all textures are stored in di.assets
    //fetch a reference to a circle texture for my ball object
    private var counter = di.assets.createLabel("", size = FontSize.F18, color = Color.BLACK)
    private val tex = di.assets.circle
    //var bounceCounter1: TextLabel = di.assets.createLabel("yes", FontSize.F20, Color.BLACK)


    private var elapsedTime = 0f
    private var isBlue = true

    //motion params
    val velocity = Vector2()
    //todo.note - gravity has a negative value as down is -ve in y axis. Unit is pixel/s^2
    val gravity = -200f

    var b:Int = 0

    init{

        //set color to blue
        color.set(0f,0f,1f,1f)
        isBlue = true
        velocity.x = 50f
        //addActor(bounceCounter1)
        addActor(counter)
        //addNumber(4)
    }

    fun setRadius(rad: Float) {
        //todo.note - all gameObjects have width and height, for a ball they are equal to 2 times radius
        setSize(2*rad, 2*rad)
    }

    fun addNumber(num: Int) {

//        bounceCounter1.setText(num.toString())
//        bounceCounter1.setSize(Values.VIRTUAL_WIDTH, Values.VIRTUAL_HEIGHT)
//        bounceCounter1.setPosition(Values.VIRTUAL_WIDTH/2, Values.VIRTUAL_HEIGHT/2, Align.center)
        counter.setText(num.toString())
        counter.setSize(counter.reqHeight * 2, counter.reqHeight * 2)
        counter.setPosition(width, 0f, Align.bottomLeft)
//        counter.debug = true
        //addActor(counter)
    }

    /**
     * Act is called at start of every frame.
     * Takes dt as a parameter
     * Used to simulate the game world
     *
     * dt - delta time. The time elapsed since last frame
     */
    override fun act(dt: Float) {
        //todo.note - up is +ve on y axis
        //update ball velocity based on total acceleration
        //since only gravity is present, only y velocity is updated
        velocity.y += gravity * dt

        //move ball based on velocity
        this.moveBy(velocity.x * dt,velocity.y * dt)

        //change ball color every 0.5 seconds
        elapsedTime += dt
        if(elapsedTime > 0.5f){
            //elapsedTime -= 0.5f
            elapsedTime = 0f
            isBlue = !isBlue
        }

        if(isBlue){
            color.lerp(1f,0f,0f,1f,0.1f)
        }
        else{
            color.lerp(0f,0f,1f,1f,0.1f)
        }
    }

    /**
     * Draw is called after act each frame
     * Takes a batch for drawing textures to screen
     * can be used to refresh the screen by drawing objects to their new location calculated in act
     */
    override fun draw(batch: Batch, parentAlpha: Float) {
        //change the batch color to balls color.
        //todo.note - Batch color applies a tint to entire texture while drawing. circle texture is white so it is drawn in the selected color
        batch.color = color

        //draw circle tex at ball's position and size
        batch.draw(tex, x, y, width, height)

        super.draw(batch, parentAlpha)

        //ALWAYS revert batch color back to white
        batch.color = Color.WHITE
    }
}