package com.example.attendance.presentation.checkin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Suppress("ParamsComparedByRef")
@Composable
fun KioskRoute(
    onOpenAdmin: () -> Unit,
    viewModel: KioskViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val latestOpenAdmin by rememberUpdatedState(onOpenAdmin)

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect){
                KioskUiEffect.OpenAdmin -> latestOpenAdmin()
            }
        }
    }
    KioskScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}