package net.obviam.nemo

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

data class Nemo(
        val position: Vector2 = Vector2(0f, 0f),
        var state: State = State.IDLE,
        var facingLeft: Boolean = true,
        var speed: Float = 160f,
        var stateTime: Float = 0f,
        var acceleration: Vector2 = Vector2(0f, 0f),
        var velocity: Vector2 = Vector2(0f, 0f),
        var bounds: Rectangle = Rectangle()) {

    enum class State {
        IDLE, RUNNING, JUMPING, DYING
    }
}

