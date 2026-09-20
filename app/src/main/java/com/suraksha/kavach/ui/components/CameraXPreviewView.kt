package com.suraksha.kavach.ui.components

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CenterFocusWeak
import androidx.compose.material.icons.rounded.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.suraksha.kavach.ui.theme.LavenderBlue
import com.suraksha.kavach.ui.theme.SageGreenSafe

/**
 * CameraXPreviewView binds a live CameraX preview if hardware camera is accessible,
 * or gracefully renders a simulated optical scanning target with laser reticle.
 */
@Composable
fun CameraXPreviewView(
    isScanning: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasCameraHardware by remember { mutableStateOf(false) }

    // Laser animation
    val infiniteTransition = rememberInfiniteTransition(label = "laser")
    val laserY by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Reverse),
        label = "laserY"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF0B0F19))
            .border(1.dp, LavenderBlue.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Live CameraX Preview Binding
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    try {
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                        hasCameraHardware = true
                    } catch (e: Exception) {
                        hasCameraHardware = false
                    }
                }, ContextCompat.getMainExecutor(ctx))
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Viewfinder Target Overlay
        Box(
            modifier = Modifier
                .size(90.dp)
                .border(2.dp, if (isScanning) SageGreenSafe else LavenderBlue, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (isScanning) {
                // Animated laser line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .offset(y = (laserY * 80 - 40).dp)
                        .background(SageGreenSafe)
                )
            }
            Icon(
                imageVector = Icons.Rounded.CenterFocusWeak,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(36.dp)
            )
        }

        // Subtitle pill
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.Black.copy(alpha = 0.65f))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = "CameraX Isolated Framebuffer • No Cloud Bleed",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
