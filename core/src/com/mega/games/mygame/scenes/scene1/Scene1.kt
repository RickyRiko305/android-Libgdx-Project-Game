package com.mega.games.mygame.scenes.scene1

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.values.Values
import com.mega.games.mygame.asset.FontSize
import com.mega.games.engine.scenes.Scene
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.scenes.scene1.gameObjects.Ball
import com.mega.games.mygame.scenes.scene1.gameObjects.Ground
import com.mega.games.mygame.utils.assets
import kotlin.math.abs

class Scene1(private val di: DI) : Scene(di){
    //todo.note - Scene follows a 2D cartesian coordinate system with origin at lower left
    //Vector2 is a pair of floats
    private val screenCenter = Vector2(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT/2f)

    private val assets = di.assets

    private val groundHeight = 20f;
    private val ballRadius = 20f;

    //gameObjects
    private lateinit var ball: Ball
    private lateinit var ground: Ground

    private var counter = assets.createLabel("Counter" + " - ", size = FontSize.F14, color = Color.BLACK)

    //init is the constructor
    init {
        addBall()
        addGround()
        addTitle()
        //addLeftWall()
        //addRightWall()
        addBounceCounter()
    }

    private fun addBall() {
        //create a new ball instance
        ball = Ball(di)
        ball.setRadius(ballRadius)
        ball.setPosition(screenCenter.x, screenCenter.y, Align.center)

        //todo.note - uncomment below line to see ball's drawing bounds. You can use (ctrl + /) on a line to toggle comment
        ball.debug = true

        addObjToScene(ball)
    }

    private fun addGround() {
        ground = Ground(di)
        ground.setPosition(0f,0f)
        ground.setSize(Values.VIRTUAL_WIDTH, groundHeight)

        //todo.note - A gameObject will only be drawn if it is added to the scene
        addObjToScene(ground)
    }

    private fun addTitle() {
        //todo.note - you can add text using labels like below
        val title = assets.createLabel("Jumping Ball", size = FontSize.F14, color = Color.BLACK)

        //place label at top center of screen
        title.setPosition(Values.VIRTUAL_WIDTH/2f, Values.VIRTUAL_HEIGHT, Align.top)

//        label.debug = true
        title.debug = true

        addObjToScene(title)
    }

    private fun addRightWall() {
        ground = Ground(di)
        ground.setPosition(Values.VIRTUAL_WIDTH,0f)
        ground.setSize(groundHeight, Values.VIRTUAL_WIDTH)

        //todo.note - A gameObject will only be drawn if it is added to the scene
        addObjToScene(ground)
    }

    private fun addLeftWall() {
        ground = Ground(di)
        ground.setPosition(0f ,0f)
        ground.setSize(groundHeight, Values.VIRTUAL_WIDTH)

        //todo.note - A gameObject will only be drawn if it is added to the scene
        addObjToScene(ground)
    }

    private fun addBounceCounter() {
        //counter = assets.createLabel("Counter" + " - " + ball.b, size = FontSize.F14, color = Color.BLACK)
        counter.setText("Counter" + " - " + ball.b.toString())
        //counter.setText("Counter" + " - " + ball.velocity.y.toString())
        counter.setPosition(Values.VIRTUAL_WIDTH/30f, Values.VIRTUAL_HEIGHT * (48f/50f), Align.topLeft)

//        label.debug = true

        addObjToScene(counter)
    }

    /**
     * Act is called at start of every frame.
     * Takes dt as a parameter
     * Used to simulate the game world
     *
     * dt - delta time. The time elapsed since last frame
     */
    override fun act(dt: Float) {
        //check ball's collision with floor in a simple way
        if(ball.y < ground.y + groundHeight){
            ball.y = ground.y + groundHeight
            ball.velocity.y *= -1
            ball.b += 1
            ball.addNumber(ball.b)
            addBounceCounter()
        }

        if((ball.x >= abs(Values.VIRTUAL_WIDTH) - 30f) || (ball.x <= 0f)){
            ball.velocity.x *= -1
        }
//        if(ball.velocity.y < 0 ){
//            addBounceCounter()
//        }
    }
}

/**
 * Complete below tasks. you can make your own decisions and choices wherever required
 *
 * [x]- The ball starts with 0 forward velocity, Give it a constant forward velocity of your choice
 * [x]- Ball will go out of the screen. Add collision logic for ball with screen edges so that it bounces off the sides
 * [x]- Add a text counter on the ball which shows the number of times the ball has bounced
 * [x]- Currently ball color changes instantaneously. Transition it smoothly from red to blue.
 *
 * - Notice that if you keep the game running, ball's jumping will slowly die down. Can you guess Why? Write your answer below
 *   -->while the ball is hitting the velocity of the ball is changing but the position of the ball is still updating, and since its now
 *     decelarating which may be result in loss in height.
 *
 */