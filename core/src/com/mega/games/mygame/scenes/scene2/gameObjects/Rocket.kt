package com.mega.games.mygame.scenes.scene2.gameObjects

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.engine.values.Values
import com.mega.games.mygame.utils.assets

class Rocket (private val di: DI): GameObject() {
    private var firingTex = di.assets.rocketFiring
    private var normalTex = di.assets.rocket

    private var currentTex = normalTex

    private var isFiring = false
    private var isGoingRight = true
    private var isLeftPressed = false
    private  var isRightPressed = false

    //physics data
    private val thrust = 1f
    private val maxVel = 100f
    private val velocity = Vector2()

    init{
        val scl = 0.5f
        setSize(currentTex.width * scl, currentTex.height*scl)

        //todo.note - to make a GameObject receive user input, below function needs to be called
        this.addInputListener()
    }

    //called when a key is pressed
    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.UP -> {
                isFiring = true
                currentTex = firingTex
            }
            Input.Keys.LEFT -> {
                isGoingRight = false
                isLeftPressed = true
                //rotation = 180f
                //rotation += 5f
            }
            Input.Keys.RIGHT -> {
                isGoingRight = true
                isRightPressed = true
                //rotation -= 5f
            }
        }
        return super.keyDown(keycode)
    }

    //called when a key is released
    override fun keyUp(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.UP -> {
                isFiring = false
                currentTex = normalTex
            }
            Input.Keys.LEFT -> {
                //isGoingRight = false
                isLeftPressed = false
                //rotation = 180f
                //rotation += 5f
            }
            Input.Keys.RIGHT -> {
                //isGoingRight = true
                isRightPressed = false
                //rotation -= 5f
            }
        }
        return super.keyUp(keycode)
    }

    override fun act(dt: Float) {
        //accelerate if player is firing the rocket
        //since currently player can only move on x axis, calculations are simplified and only x component needs to be handled
        if(isFiring){
//            if(isGoingRight){
//                //velocity.add(thrust * dt, 0f);
//            }else{
//                //velocity.add(-thrust * dt, 0f);
//            }
//            if(isLeftPressed){
//                //velocity.add(thrust * dt, 0f);
//                rotation += 5f
//            }else{
//                //velocity.add(-thrust * dt, 0f);
//            }
//            if(isRightPressed){
//                //velocity.add(thrust * dt, 0f);
//                rotation -= 5f
//            }else{
//                //velocity.add(-thrust * dt, 0f);
//            }
            velocity.add(MathUtils.cosDeg(rotation) * thrust * dt, MathUtils.sinDeg(rotation) * thrust * dt);
        }

        if(isLeftPressed){
            rotation += dt*45f
        }

        if(isRightPressed){
            rotation -= dt*45f
        }

        //limit velocity magnitude
        if(velocity.x > maxVel){
            velocity.x = maxVel
        }

        moveBy(velocity.x, velocity.y)

        if(x > Values.VIRTUAL_WIDTH){
            x-=Values.VIRTUAL_WIDTH
        }

        if(x < 0){         //reset the
            x+=Values.VIRTUAL_WIDTH
        }

        if(y >= Values.VIRTUAL_HEIGHT-40){
            y-=Values.VIRTUAL_HEIGHT
        }

        if(y <= 0){         //reset the
            y+=Values.VIRTUAL_HEIGHT
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        //todo.note - draw function has many overrides. Below override can be used to draw a rotate and scaled texture
        //set the origin of rotation to center, scale is 1 because it is handled explicitly in size while calling setSize in init
        batch.draw(TextureRegion(currentTex), x, y, width/2, height/2, width, height,1f,1f,rotation)
        batch.draw(TextureRegion(currentTex), x-Values.VIRTUAL_WIDTH, y, width/2, height/2, width, height,1f,1f,rotation)
        batch.draw(TextureRegion(currentTex), x, y-Values.VIRTUAL_HEIGHT, width/2, height/2, width, height,1f,1f,rotation)
    }
}
