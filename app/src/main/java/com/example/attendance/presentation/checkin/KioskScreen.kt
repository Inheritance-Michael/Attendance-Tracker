package com.example.attendance.presentation.checkin


import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.ui.theme.AppColors
import com.example.attendance.ui.theme.StatusStyle
import com.example.attendance.ui.theme.style


@Composable
fun KioskScreen(
    state: KiosUiState,
    onEvent: (KioskUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppColors.Primary)
    ){
        ScanContent(state, onEvent)
        (state.phase as?  KioskPhase.Result)?.let { ResultContent(it, Modifier.fillMaxSize()) }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun ResultContent(x0: KioskPhase.Result, x1: Modifier) {
    val style = x0.outcome.style()
    val compact = LocalConfiguration.current.screenWidthDp < 600
    val countdown = remember { Animatable(1f) }

    LaunchedEffect(x0) {
        countdown.animateTo(0f, tween( KioskTiming.RESULT_DISPLAY_MS.toInt(), easing = LinearEasing))
    }

    Box(
        x1.background(style.background)
    ){
        Column(
            Modifier.align(Alignment.Center).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(if(compact) 16.dp else 28.dp)
        ) {
            val avatar = if (compact) 120.dp else 200.dp
            if(x0.initials.isNotEmpty()){
                StatusAvatar(x0.initials, style, avatar)
            }else{
                Box(
                    Modifier
                        .size(avatar)
                        .background(style.strong, CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        style.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(avatar * 0.5f)
                    )
                }
            }
            Text(
                text = x0.headline,
                fontSize = if(compact) 36.sp else 72.sp,
                fontWeight = FontWeight.Bold,
                color = style.deep,
                textAlign = TextAlign.Center
            )
            val line = listOf(x0.name, x0.classLine).filter{ it.isNotEmpty()}.joinToString { " . " }
            if (line.isNotEmpty()){
                Text(
                    text = line,
                    fontSize = if(compact) 20.sp else 32.sp,
                    color = AppColors.Ink,
                    textAlign = TextAlign.Center
                )
            }
            if(x0.pill.isNotEmpty()){
                Text(
                    text = x0.pill,
                    fontSize = if (compact) 18.sp else 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color= Color.White,
                    modifier = Modifier.background(style.strong, RoundedCornerShape(40.dp))
                        .padding(horizontal = 32.dp, vertical = 14.dp)
                )
            }
        }
        Column(
            Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(horizontal = 40.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Returning to scanner…", fontSize = 18.sp, color = AppColors.Ink)
            LinearProgressIndicator(
                progress = { countdown.value },
                modifier = Modifier.fillMaxWidth().height(10.dp).clip(RoundedCornerShape(5.dp)),
                color = style.strong, trackColor = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun StatusAvatar(initials: String, style: StatusStyle, size: Dp) {
    Box {
        Box(Modifier.size(size).background(AppColors.Primary, CircleShape), contentAlignment = Alignment.Center) {
            Text(initials, color = Color.White, fontSize = (size.value * 0.36f).sp, fontWeight = FontWeight.Bold)
        }
        Box(
            Modifier.align(Alignment.BottomEnd).offset(8.dp, 8.dp).size(size * 0.36f)
                .background(style.strong, CircleShape).border(6.dp, style.background, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(style.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(size * 0.2f))
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun ScanContent(
    state: KiosUiState,
    onEvent: (KioskUiEvent) -> Unit
) {
    val wide = LocalConfiguration.current.screenWidthDp >= 600
    val onScanned = {text: String -> onEvent(KioskUiEvent.QrScanned(text))}
    val prompt = @Composable{
        Text(
            text = "Hold Your ID card up to the camera",
            style = if(wide) MaterialTheme.typography.headlineLarge else MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
    if (wide){
        Row(
            Modifier.fillMaxSize()
        ) {
            Column(
                Modifier.weight(3f)
                    .fillMaxHeight()
                    .padding(40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ){
                Header(onEvent)
                CameraPanel(
                    onScanned,
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
                prompt()
            }
        }
    }
    else{
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header { onEvent }
            CameraPanel(onScanned, Modifier.weight(1f).fillMaxWidth(). clip(RoundedCornerShape(20.dp)))
            prompt()
            InfoPanel(state, compact = true, Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)))
        }
    }
}


@Composable
private fun InfoPanel(
    x0: KiosUiState,
    compact: Boolean,
    x2: Modifier
) {
    Column(
        x2.background(Color.White).padding(if (compact) 20.dp else 48.dp),
        verticalArrangement = Arrangement.spacedBy(if (compact) 12.dp else 24.dp)
    ) {
        Text(x0.orgName, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Muted)
        Text(
            x0.timeText,
            fontSize = if (compact) 56.sp else 128.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink
        )
        Text(x0.dateText, fontSize = if (compact) 18.sp else 24.sp, color = AppColors.Muted)
        if (!compact) Spacer(Modifier.weight(1f))
        Column(
            Modifier.fillMaxWidth().background(AppColors.Surface, RoundedCornerShape(20.dp)).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Signed in today", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("${x0.present} of ${x0.total}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            LinearProgressIndicator(
                progress = { if (x0.total == 0) 0f else x0.present / x0.total.toFloat() },
                modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)),
                color = AppColors.Primary, trackColor = Color(0xFFDCE3EC)
            )
        }
    }
}

@Composable
private fun CameraPanel(x0: (String) -> Unit, x1: Modifier) {
   Box(
       x1
           .clip(RoundedCornerShape(28.dp))
           .background(AppColors.CameraDark),
       contentAlignment = Alignment.Center
   ){
       CameraPermissionGate {
           QrScannerView(
               onQrScanner = x0,
               modifier = Modifier.fillMaxSize()
           )
           Box(
               Modifier
                   .fillMaxWidth(0.6f)
                   .aspectRatio(1.5f)
                   .border(4.dp, Color.White, RoundedCornerShape(24.dp))
           )
       }
   }
}

@Composable
private fun Header(x0: (KioskUiEvent) -> Unit) {
    Text(
        text = "CheckIn",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Primary,
        modifier = Modifier.combinedClickable(
            onClick = {
                TODO()
            },
            onLongClick = {x0(KioskUiEvent.AdminRequested)},
            onLongClickLabel = "Open admin"
        ).padding(vertical = 8.dp)
    )
}