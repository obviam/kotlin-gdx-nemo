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

    override fun create() {
        batch = SpriteBatch()
        img = Texture("images/nemo_01.png")
        nemo = Nemo()
        Gdx.input.inputProcessor = KeyInputProcessor()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(img, nemo.position.x, nemo.position.y)
        batch.end()
    }

    data class Nemo(val position: Vector2 = Vector2(0f, 0f))

    inner class KeyInputProcessor : InputAdapter() {

        override fun keyDown(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.LEFT -> moveCharacter(Direction.LEFT)
                Input.Keys.RIGHT -> moveCharacter(Direction.RIGHT)
                else -> return false
            }
            return true
        }
    }

    fun moveCharacter(direction: Direction) {
        if (direction.equals(Direction.LEFT) && nemo.position.x >= 10) {
            nemo.position.x -= 10
        }
        if (direction.equals(Direction.RIGHT) && nemo.position.x <= 300) {
            nemo.position.x += 10
        }
    }
}

enum class Direction {
    LEFT, RIGHT
}
