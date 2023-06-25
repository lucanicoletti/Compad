package com.lucanicoletti.compad

data class CompadCallbacks(
    val moveRight: (() -> Unit)? = null,
    val moveLeft: (() -> Unit)? = null,
    val moveUp: (() -> Unit)? = null,
    val moveDown: (() -> Unit)? = null,
    val moveUpRight: (() -> Unit)? = null,
    val moveDownRight: (() -> Unit)? = null,
    val moveUpLeft: (() -> Unit)? = null,
    val moveDownLeft: (() -> Unit)? = null,
    val onRelease: (() -> Unit)? = null
)