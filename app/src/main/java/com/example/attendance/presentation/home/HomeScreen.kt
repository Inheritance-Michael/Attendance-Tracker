package com.example.attendance.presentation.home

import android.annotation.SuppressLint
import android.icu.number.Scale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen(
    state: HomeScreenUiState,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onEvent: (HomeUiEvent) -> Unit
) {
    val wide = LocalConfiguration.current.screenWidthDp >= 600

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        val localModifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()

        if(wide){
            Row(
                modifier = localModifier,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
              //  PeoplePane
            }
        }
    }
}