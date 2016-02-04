package net.obviam.nemo

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

class Game : ApplicationAdapter() {
    internal lateinit var batch: SpriteBatch
    internal lateinit var idleLeft: TextureRegion
    internal lateinit var idleRight: TextureRegion
    internal lateinit var nemo: Nemo
    internal var leftPressed: Boolean = false
    internal var rightPressed: Boolean = false
    internal lateinit var runLeftAnimation: Animation
    internal lateinit var runRightAnimation: Animation
    internal lateinit var currentNemoFrame: TextureRegion

    override fun create() {
        batch = SpriteBatch()

        // --- Load textures ---
        idleLeft = TextureRegion(Texture("images/nemo_01.png"))
        // --- Create image for idle facing right
        idleRight = TextureRegion(idleLeft)
        // --- Flipping the original on (not around) the X axis (horizontally)
        idleRight.flip(true, false)

        // -- Instantiate the gdx Array to hold the animations frames for running left
        val runLeftFrames = Array<TextureRegion>()
        // -- Load textures from individual files
        for (i in 1..6) runLeftFrames.add(TextureRegion(Texture("images/nemo_0${i}.png")))
        // -- Create the animation from the loaded frames
        runLeftAnimation = Animation(0.06f, runLeftFrames)

        // --- Same for the right animation but flipping the already loaded frames
        // --- used for the left animation
        val rightRunFrames = Array<TextureRegion>()
        // --- ! Note ! - have to create a new TextureRegion for each frame
        for (frame in runLeftFrames) rightRunFrames.add(TextureRegion(frame))
        rightRunFrames.map { it.flip(true, false) }
        runRightAnimation = Animation(0.06f, rightRunFrames)

        // --- Setting the current frame
        currentNemoFrame = idleLeft

        // -- Creating Nemo with defaults
        nemo = Nemo()
        Gdx.input.inputProcessor = KeyInputProcessor()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        updateNemo()
        batch.begin()
        if (nemo.state == Nemo.State.RUNNING) {
            if (nemo.facingLeft) {
                currentNemoFrame = runLeftAnimation.getKeyFrame(nemo.stateTime, true)
            }
            else {
                currentNemoFrame = runRightAnimation.getKeyFrame(nemo.stateTime, true)
            }
        }
        else {
            if (nemo.facingLeft) {
                currentNemoFrame = idleLeft
            } else {
                currentNemoFrame = idleRight
            }
        }
        batch.draw(currentNemoFrame, nemo.position.x, nemo.position.y)
        batch.end()
    }

    private fun updateNemo(delta: Float = Gdx.graphics.deltaTime) {
        if (leftPressed || rightPressed) {
            nemo.state = Nemo.State.RUNNING
            nemo.stateTime += delta
            nemo.facingLeft = leftPressed
        } else {
            nemo.state = Nemo.State.IDLE
        }

        if (nemo.state == Nemo.State.RUNNING) {
            if (nemo.facingLeft && nemo.position.x >= 10) {
                nemo.position.x -= nemo.speed * delta
            }
            else if (nemo.position.x < 610) {
                nemo.position.x += nemo.speed * delta
            }
        }
    }

    data class Nemo(
            val position: Vector2 = Vector2(0f, 0f),
            var state: State = State.IDLE,
            var facingLeft: Boolean = true,
            var speed: Float = 160f,
            var stateTime: Float = 0f) {

        enum class State {
            IDLE, RUNNING
        }
    }

    inner class KeyInputProcessor : InputAdapter() {

        override fun keyDown(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.LEFT -> leftPressed = true
                Input.Keys.RIGHT -> rightPressed = true
                else -> return false
            }
            return true
        }

        override fun keyUp(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.LEFT -> leftPressed = false
                Input.Keys.RIGHT -> rightPressed = false
                else -> return false
            }
            return true
        }
    }
}