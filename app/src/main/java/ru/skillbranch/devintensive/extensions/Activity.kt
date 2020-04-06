package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    if(currentFocus != null) imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean{

    val rect = Rect()
    val rootView = this.window.decorView.rootView
    rootView.getWindowVisibleDisplayFrame(rect)

    val heightDiff = rootView.height - (rect.bottom - rect.top)
    return heightDiff > rootView.height/ 4

}

fun Activity.isKeyboardClosed(): Boolean{
    return !isKeyboardOpen()
}

