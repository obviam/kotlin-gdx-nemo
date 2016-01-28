package net.obviam.nemo

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
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
        // Load Nemo's texture
        img = Texture("images/nemo_01.png")
        nemo = Nemo()
    }

    override fun render() {
        // render the screen black
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        // render the texture at Nemo's position
        batch.draw(img, nemo.position.x, nemo.position.y)
        batch.end()
    }

    /* Declare the Nemo data class */
    data class Nemo(val position: Vector2 = Vector2(0f, 0f))
}
