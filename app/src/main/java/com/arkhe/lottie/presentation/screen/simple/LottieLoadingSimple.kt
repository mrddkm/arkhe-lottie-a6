package com.arkhe.lottie.presentation.screen.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkhe.lottie.presentation.screen.simple.components.BasicLoading
import com.arkhe.lottie.presentation.screen.simple.components.ControlledLoading
import com.arkhe.lottie.presentation.screen.simple.components.CustomAnimation
import com.arkhe.lottie.presentation.screen.simple.components.MultiStepLoading
import com.arkhe.lottie.presentation.screen.simple.components.ProgressLoading

@Composable
fun LottieLoadingSimple() {
    var selectedExample by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF667eea),
                        Color(0xFF764ba2)
                    )
                )
            )
            .padding(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Lottie Loading",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (selectedExample) {
                0 -> BasicLoading()
                1 -> ProgressLoading()
                2 -> ControlledLoading()
                3 -> MultiStepLoading()
                4 -> CustomAnimation()
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            val examples = listOf("Basic", "Progress", "Control", "Multi-Step", "Custom")

            examples.forEachIndexed { index, title ->
                Button(
                    onClick = { selectedExample = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedExample == index)
                            Color.White.copy(alpha = 0.3f)
                        else
                            Color.White.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}