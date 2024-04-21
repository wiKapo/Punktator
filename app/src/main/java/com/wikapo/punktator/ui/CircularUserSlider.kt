package com.wikapo.punktator.ui

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularUserSlider() {
    val configuration = LocalConfiguration.current
    val points = remember { mutableListOf(0.0, 0.0, 0.0, 0.0) }

//    val users = mutableListOf<User>()
//    if (userAmount == 0) users.add(User("TEST"))
//    else for (i in 1..userAmount.absoluteValue) {
//        users.add(
//            if (userAmount == 1)
//                User("USER $i")
//            else
//                User(
//                    "USER $i",
//                    startAngle = (360.0 / userAmount.absoluteValue.toDouble()) * i
//                )
//        )
//    }
//    Log.w("USERS", "${users[0].name} | ${users[0].startAngle}")

    Box(
        modifier = Modifier
            .width(
                (if (configuration.orientation == ORIENTATION_PORTRAIT) configuration.screenWidthDp
                else configuration.screenHeightDp).dp
            )
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(points.toString())
        }

        //Code from https://stackoverflow.com/a/70424604 with some changes made by me
        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
        ) {
            drawCircle(
                color = Color.Black.copy(alpha = 0.10f),
                style = Stroke(20f),
                radius = size.minDimension / 2
            )
        }
        for (pos in 0..<points.size) {

            var shapeCenter by remember { mutableStateOf(Offset.Zero) }
            val startPos = if (points.size == 1) 0.0 else ((360.0 / points.size * (pos + 1)) - 45.0)
            var handlePosition by remember { mutableStateOf(Offset.Zero) }
            var angle by remember { mutableDoubleStateOf(startPos) }
            var changeAngle by remember { mutableDoubleStateOf(0.0) }
            val lastAnglePositive = remember { mutableStateOf(true) }
            val rotations = remember { mutableIntStateOf(0) }
            Row(Modifier.padding(top = (startPos / 5).dp)) {
                Text("${rotations.intValue} | ${lastAnglePositive.value}")
            }
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { },
                        onDragEnd = {
                            changeAngle = startPos - angle + (360 * rotations.intValue)
                            rotations.intValue = 0
                            angle = startPos
                            points[pos] += changeAngle
                        }
                    ) { change, dragAmount ->
                        handlePosition += dragAmount

                        angle = getRotationAngle(
                            handlePosition,
                            shapeCenter,
                            lastAnglePositive,
                            rotations
                        )
                        change.consume()
                    }
                }
                .padding(30.dp)) {
                shapeCenter = center

                val radius = size.minDimension / 2

                val x = (shapeCenter.x + cos(Math.toRadians(angle)) * radius).toFloat()
                val y = (shapeCenter.y + sin(Math.toRadians(angle)) * radius).toFloat()

                handlePosition = Offset(x, y)
                drawCircle(color = Color.Cyan, center = handlePosition, radius = 60f)
            }
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
        if (lastAnglePositive.value && angle > 350) {
            rotations.intValue++
        }
        lastAnglePositive.value = false
    } else {
        if (!lastAnglePositive.value && angle < 10) {
            rotations.intValue--
        }
        lastAnglePositive.value = true
    }
    return angle
}

@Preview(showBackground = true)
@Composable
private fun CircularSliderPreview() {
    CircularUserSlider()
}