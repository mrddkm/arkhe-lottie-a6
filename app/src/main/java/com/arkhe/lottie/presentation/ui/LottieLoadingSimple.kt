package com.arkhe.lottie.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arkhe.lottie.R
import kotlinx.coroutines.delay

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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Header
        Text(
            text = "Lottie Loading Examples",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        // Main Content Area
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (selectedExample) {
                0 -> BasicLoadingExample()
                1 -> ProgressLoadingExample()
                2 -> ControlledLoadingExample()
                3 -> MultiStepLoadingExample()
                4 -> CustomAnimationExample()
            }
        }

        // Bottom Navigation
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

// 1. Basic Loading dari Assets
@Composable
fun BasicLoadingExample() {
    Card(
        modifier = Modifier
            .size(280.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Lottie Animation dari res/raw/loading_animation.json
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.loading_infinite)
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loading...",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Basic infinite loop animation",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// 2. Progress loading with manual control
@Composable
fun ProgressLoadingExample() {
    var progress by remember { mutableFloatStateOf(0f) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            while (progress < 1f) {
                delay(50)
                progress += 0.02f
            }
            delay(500)
            progress = 0f
            isLoading = false
        }
    }

    Card(
        modifier = Modifier
            .size(280.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Progress-controlled Lottie
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.loading_files)
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f),
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${(progress * 100).toInt()}%",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isLoading) {
                        progress = 0f
                        isLoading = true
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f)
                )
            ) {
                Text(
                    text = if (isLoading) "Loading..." else "Start Progress",
                    color = Color.White
                )
            }
        }
    }
}

// 3. Controlled Loading with play/pause/speed
@Composable
fun ControlledLoadingExample() {
    var isPlaying by remember { mutableStateOf(true) }
    var speed by remember { mutableFloatStateOf(1f) }

    Card(
        modifier = Modifier
            .size(280.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.ios_spinner)
            )
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                isPlaying = isPlaying,
                speed = speed
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Control Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { isPlaying = !isPlaying },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = if (isPlaying) "⏸️ Pause" else "▶️ Play",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = { speed = if (speed == 1f) 2f else 1f },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = "${speed.toInt()}x Speed",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Animation Controls",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// 4. Multi-Step Loading
@Composable
fun MultiStepLoadingExample() {
    var currentStep by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }

    val steps = listOf(
        "Initializing...",
        "Loading assets...",
        "Processing data...",
        "Finalizing...",
        "Complete!"
    )

    LaunchedEffect(isLoading) {
        if (isLoading) {
            for (i in 0 until steps.size) {
                currentStep = i
                delay(1200)
            }
            delay(1000)
            currentStep = 0
            isLoading = false
        }
    }

    Card(
        modifier = Modifier
            .size(280.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (isLoading) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.ios_spinner)
                )
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = steps[currentStep],
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = { (currentStep + 1).toFloat() / steps.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f),
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )

                Text(
                    text = "Step ${currentStep + 1} of ${steps.size}",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Text(
                    text = "Multi-Step Process",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { isLoading = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    )
                ) {
                    Text("Start Process", color = Color.White)
                }
            }
        }
    }
}

// 5. Custom Animation with URL/JSON String
@Composable
fun CustomAnimationExample() {
    var showCustom by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(280.dp)
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (showCustom) {
                // Example Load from the URL (Need Network Permission)
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.Url("https://assets5.lottiefiles.com/packages/lf20_V9t630.json")
                )
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(120.dp)
                )

                Button(
                    onClick = { showCustom = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Back", color = Color.White)
                }
            } else {
                Text(
                    text = "Custom Animation",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Load from URL or JSON string",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showCustom = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    )
                ) {
                    Text("Load Remote", color = Color.White)
                }
            }
        }
    }
}