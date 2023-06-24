package com.lucanicoletti.compad

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucanicoletti.compad.CompadMeasures.radius
import kotlin.math.abs
import kotlin.math.sqrt

private object CompadMeasures {
    var width: Int = 0
    var height: Int = 0
    val centerPoint = Offset.Zero

    fun radius(interactionSize: Float): Float = (width / 2) - interactionSize
}

@Composable
fun Compad(modifier: Modifier = Modifier) {
    val touchPoint = remember { mutableStateOf(Offset.Zero) }
    val shouldDraw = remember { mutableStateOf(false) }
    val interactionTouchSize = with(LocalDensity.current) { 32.dp.toPx() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .shadow(8.dp, shape = CircleShape)
            .background(Color.White)
            .onGloballyPositioned { coordinates ->
                CompadMeasures.width = coordinates.size.width
                CompadMeasures.height = coordinates.size.height
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { touch ->
                        shouldDraw.value = true
                        touchPoint.value = touch
                    },
                    onDrag = { change, _ ->
                        shouldDraw.value = true
                        val normalisedPoint = normaliseTouchPoint(change.position)
                        if (isPointInside(normalisedPoint, interactionTouchSize)) {
                            touchPoint.value = change.position
                        } else {
                            touchPoint.value =
                                getPointOnBorderFrom(normalisedPoint, interactionTouchSize)
                        }
                    },
                    onDragEnd = {
                        shouldDraw.value = false
                    },
                )
            },
    ) {
        Canvas(modifier = Modifier) {
            if (shouldDraw.value) {
                drawCircle(
                    color = Color.LightGray,
                    alpha = 0.8f,
                    radius = interactionTouchSize + 2f,
                    center = touchPoint.value
                )
                drawCircle(
                    color = Color.LightGray,
                    alpha = 0.6f,
                    radius = interactionTouchSize + 4f,
                    center = touchPoint.value
                )
                drawCircle(
                    color = Color.LightGray,
                    alpha = 0.4f,
                    radius = interactionTouchSize + 6f,
                    center = touchPoint.value
                )
                drawCircle(
                    color = Color.LightGray,
                    alpha = 0.2f,
                    radius = interactionTouchSize + 8f,
                    center = touchPoint.value
                )
                drawCircle(
                    color = Color.White,
                    radius = interactionTouchSize,
                    center = touchPoint.value
                )
            }
        }
    }
}

fun getPointOnBorderFrom(position: Offset, interactionSize: Float): Offset {
    val pointX =
        radius(interactionSize) * ((position.x) / sqrt((position.x * position.x) + (position.y * position.y)))
    val pointY =
        radius(interactionSize) * ((position.y) / sqrt(((position.x * position.x) + (position.y * position.y))))
    return denormaliseTouchPoint(Offset(pointX, pointY))
}

private fun denormaliseTouchPoint(point: Offset): Offset {
    return Offset(point.x + (CompadMeasures.width / 2f), point.y + (CompadMeasures.height / 2f))
}

private fun normaliseTouchPoint(point: Offset): Offset {
    return Offset(point.x - (CompadMeasures.width / 2f), point.y - (CompadMeasures.height / 2f))
}

private fun isPointInside(point: Offset, interactionSize: Float): Boolean {
    val dx = abs(point.x - CompadMeasures.centerPoint.x)
    val dy = abs(point.y - CompadMeasures.centerPoint.y)
    if (dx + dy <= radius(interactionSize))
        return true
    if (dx > radius(interactionSize))
        return false
    if (dy > radius(interactionSize))
        return false
    return dx * dx + dy * dy <= radius(interactionSize) * radius(interactionSize)
}

@Preview(showBackground = true)
@Composable
fun CompadPreview() {
    Compad(modifier = Modifier.padding(8.dp))
}