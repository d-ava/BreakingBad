package com.example.breakingbad.extensions

import android.app.AlertDialog
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.breakingbad.R

fun Fragment.showDialogMain(@StringRes title: Int, @StringRes message: Int) {
    showDialog(title, getString(message))
}


fun Fragment.showDialog(@StringRes title: Int, message: String) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(
            R.string.error
        ) { dialog, _ -> dialog.dismiss() }
        .setCancelable(true)
        .show()


}