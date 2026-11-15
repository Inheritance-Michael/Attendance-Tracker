package com.example.attendance.presentation.components

import android.R.attr.y
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.scaleMatrix
import com.example.attendance.domain.util.QrMatrix


@Composable
fun QrCodeImage(
    maftrix: QrMatrix,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier
            .aspectRatio(1f)
            .background(Color.White)
    ){
        val quiet = 4
        val cell = size.minDimension / (maftrix.size + quiet * 2)
        for(y in 0 until maftrix.size) for (x in 0 until maftrix.size){
            if(maftrix.isDark(x,y)){
                drawRect(
                    color = Color.Black,
                    topLeft = Offset((x + quiet) * cell, (y + quiet) * cell),
                    size= Size(cell + 0.5f, cell + 0.5f)
                )
            }
        }
    }
}