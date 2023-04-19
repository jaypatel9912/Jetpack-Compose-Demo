package com.example.map.Util

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewTreeObserver
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import java.util.Stack

@Composable
fun remeberImeState() : State<Boolean>{
    val initState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view){
        val listner = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyBoardOpen = ViewCompat.getRootWindowInsets(view)?.isVisible(WindowInsets.Type.ime()) ?: true
            initState.value = isKeyBoardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listner)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener { listner }
        }
    }

    return initState
}