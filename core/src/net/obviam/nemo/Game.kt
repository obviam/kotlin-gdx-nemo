package net.obviam.nemo

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Game : ApplicationAdapter() {
    internal lateinit var batch: SpriteBatch
    internal lateinit var img: Texture
    internal lateinit var nemo: Nemo
    internal var leftPressed : Boolean = false;
    internal var rightPressed : Boolean = false;

    override fun create() {
        batch = SpriteBatch()
        img = Texture("images/nemo_01.png")
        nemo = Nemo()
        Gdx.input.inputProcessor = KeyInputProcessor()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        updateCharacter()
        batch.begin()
        batch.draw(img, nemo.position.x, nemo.position.y)
        batch.end()
    }

    private fun updateCharacter() {
        if (leftPressed && nemo.position.x >= 10) nemo.position.x -= 10
        if (rightPressed && nemo.position.x < 610) nemo.position.x += 10

    }

    data class Nemo(val position: Vector2 = Vector2(0f, 0f))

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
