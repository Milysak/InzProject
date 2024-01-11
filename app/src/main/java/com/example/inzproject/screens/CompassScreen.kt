package com.example.inzproject.screens

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.inzproject.compass.Cardinal
import com.example.inzproject.compass.SensorDataManager
import com.example.inzproject.helpfunctions.createGradientBrush
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun CompassScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            createGradientBrush(
                listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                )
            )
        ),
    ) {
        var degree by remember { mutableStateOf(0f) }
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        DisposableEffect(Unit) {
            val dataManager = SensorDataManager(context)
            dataManager.init()

            val job = scope.launch {
                dataManager.data
                    .receiveAsFlow()
                    .onEach { degree = it }
                    .collect {

                    }
            }
            onDispose {
                dataManager.cancel()
                job.cancel()
            }
        }

        val circleColor = MaterialTheme.colorScheme.onBackground.copy(0.75f)
        val arrowPosColor = MaterialTheme.colorScheme.primary.copy(0.75f)
        val arrowNegColor = MaterialTheme.colorScheme.primary

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp)
            ) {
                drawCircle(
                    color = circleColor,
                    style = Stroke(15f),
                    center = Offset(x = size.width / 2, y = size.height / 2),
                    radius = size.width
                )

                val arrowPath = Path()
                arrowPath.moveTo(size.width / 2, (size.height * .95).toFloat())
                arrowPath.lineTo(size.width / 2 + 45, size.height / 2)
                arrowPath.lineTo(size.width / 2 - 45, size.height / 2)
                arrowPath.lineTo(size.width / 2, (size.height * .95).toFloat())

                rotate(-degree) {
                    Cardinal.values().forEach {
                        rotate(it.degree.toFloat()) {
                            drawContext.canvas.nativeCanvas.apply {
                                drawText(
                                    it.letter,
                                    size.width / 2,
                                    -(size.width / 2 + 40),
                                    Paint().apply {
                                        textSize = 75f
                                        color = circleColor.toArgb()
                                        textAlign = Paint.Align.CENTER
                                    }
                                )
                            }
                        }
                    }

                    for (i in 0..350 step 10) {
                        rotate(i.toFloat()) {
                            drawLine(
                                color = circleColor,
                                start = Offset(
                                    x = size.width / 2,
                                    ((size.height / 2) - size.width) - 25
                                ),
                                end = Offset(
                                    x = size.width / 2,
                                    ((size.height / 2) - size.width) - 10
                                ),
                                strokeWidth = 10f,
                            )
                        }
                    }

                    drawPath(arrowPath, SolidColor(arrowNegColor))
                    rotate(180f) {
                        drawPath(arrowPath, SolidColor(arrowPosColor))
                    }
                }

                drawLine(
                    color = arrowNegColor,
                    start = Offset(
                        x = size.width / 2, ((size.height / 2) - size.width) + 30
                    ),
                    end = Offset(
                        x = size.width / 2, ((size.height / 2) - size.width) - 30
                    ),
                    strokeWidth = 12f,
                )
            }
        }
    }
}