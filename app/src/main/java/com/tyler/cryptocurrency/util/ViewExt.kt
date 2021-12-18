package com.tyler.cryptocurrency.util

import android.os.SystemClock
import android.util.Log
import android.view.View

fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()

            Log.e("click", "elapsedRealtime ${lastClickTime}")
        }
    })
}