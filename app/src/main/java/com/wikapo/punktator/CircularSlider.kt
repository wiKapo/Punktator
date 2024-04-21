package com.wikapo.punktator

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wikapo.punktator.classes.User
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularSlider(userAmount: Int) {
    val configuration = LocalConfiguration.current
    var radius by remember { mutableFloatStateOf(0f) }
    var shapeCenter by remember { mutableStateOf(Offset.Zero) }
    var handleCenter by remember { mutableStateOf(Offset.Zero) }
    var angle by remember { mutableDoubleStateOf(0.0) }
    var lastAngle by remember { mutableDoubleStateOf(0.0) }
    var changeAngle by remember { mutableDoubleStateOf(0.0) }
    val lastAnglePositive = remember { mutableStateOf(true) }
    val rotations = remember { mutableIntStateOf(0) }

    val users = mutableListOf<User>()
    if (userAmount == 0) users.add(User("TEST"))
    else for (i in 1..userAmount.absoluteValue) {
        users.add(
            if (userAmount == 1)
                User("USER $i")
            else
                User(
                    "USER $i",
                    startAngle = (360.0 / userAmount.absoluteValue.toDouble()) * i
                )
        )
    }
    Log.w("USERS", "${users[0].name} | ${users[0].startAngle}")

    Box(
        modifier = Modifier
            .width(configuration.screenWidthDp.dp)
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(angle.toString())
            Text(lastAngle.toString())
            Text(changeAngle.toString())
        }

        //Code from https://stackoverflow.com/a/70424604 with some changes made by me
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { lastAngle = angle },
                        onDragEnd = {
                            changeAngle = lastAngle - angle + (360 * rotations.intValue)
                            rotations.intValue = 0
                        }
                    ) { change, dragAmount ->
                        handleCenter += dragAmount

                        angle = getRotationAngle(
                            handleCenter,
                            shapeCenter,
                            lastAnglePositive,
                            rotations
                        )
                        change.consume()
                    }
                }
                .padding(30.dp)

        ) {
            shapeCenter = center

            radius = size.minDimension / 2

            val x = (shapeCenter.x + cos(Math.toRadians(angle)) * radius).toFloat()
            val y = (shapeCenter.y + sin(Math.toRadians(angle)) * radius).toFloat()

            handleCenter = Offset(x, y)

            drawCircle(
                color = Color.Black.copy(alpha = 0.10f),
                style = Stroke(20f),
                radius = radius
            )
            drawArc(
                color = Color.Yellow,
                startAngle = lastAngle.toFloat(),
                sweepAngle = (angle - lastAngle).toFloat(),
                useCenter = false,
                style = Stroke(20f)
            )

            drawCircle(color = Color.Cyan, center = handleCenter, radius = 60f)
        }
    }
}

private fun getRotationAngle(
    currentPosition: Offset,
    center: Offset,
    lastAnglePositive: MutableState<Boolean>,
    rotations: MutableIntState
): Double {
    val (dx, dy) = currentPosition - center
    val theta = atan2(dy, dx).toDouble()

    var angle = Math.toDegrees(theta)

    if (angle < 0) {
        angle += 360.0
        if (lastAnglePositive.value && angle > 270) {
            rotations.intValue++
        }
        lastAnglePositive.value = false
    } else {
        if (!lastAnglePositive.value && angle < 90) {
            rotations.intValue--
        }
        lastAnglePositive.value = true
    }
    return angle
}

@Preview(showBackground = true)
@Composable
private fun CircularSliderPreview() {
    CircularSlider(3)
}