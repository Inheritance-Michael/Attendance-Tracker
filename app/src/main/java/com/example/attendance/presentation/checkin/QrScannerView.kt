package com.example.attendance.presentation.checkin

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanner
import java.util.concurrent.Executors

@Composable
fun QrScannerView(
    onQrScanner: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val latestCallback by rememberUpdatedState(onQrScanner)

    val previewView = remember {
        PreviewView(context).apply { scaleType = PreviewView.ScaleType.FIT_CENTER }
    }

//    DisposableEffect(lifecycleOwner) {
//        val executor = Executors.newSingleThreadExecutor()
//        //val scanner = BarcodeScanner.
//    }


}