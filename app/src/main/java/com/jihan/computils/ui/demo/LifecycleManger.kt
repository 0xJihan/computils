package com.jihan.computils.ui.demo

import androidx.compose.runtime.Composable
import com.jihan.composeutils.LifecycleManager

@Composable
fun LifecycleExample() {


    LifecycleManager(onStart = { println("▶️ Component started") },
        onResume = { println("✳️ Component resumed") },
        onPause = { println("⏸️ Component paused") },
        onStop = { println("⏹️ Component stopped") },
        onRestart = { println("🔄 Component restarted") },
        onDestroy = { println("💢 Component destroyed") },
        onDispose = { println("🗑️ Component disposed") })
}
