package com.example.instabug_task.ui.dialog

import android.R
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.example.instabug_task.databinding.CustomDialogBinding

class CustomDialog(
    context: Context,
    title: String = "",
    message: String,
    positiveText: String,
    negativeText: String,
    isCancelable: Boolean = true,
    private val onPositive: (() -> Unit)? = null,
    private val onNegative: (() -> Unit)? = null
) {
    private val dialog = Dialog(context)
    private val binding = CustomDialogBinding.inflate(LayoutInflater.from(context))

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.setCancelable(isCancelable)

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        binding.dialogTitle.text = title
        binding.dialogMessage.text = message
        binding.positiveButton.text = positiveText
        binding.negativeButton.text = negativeText

        binding.positiveButton.setOnClickListener {
            dialog.dismiss()
            onPositive?.invoke()
        }

        binding.negativeButton.setOnClickListener {
            dialog.dismiss()
            onNegative?.invoke()
        }

        val metrics = context.resources.displayMetrics
        val width = (metrics.widthPixels * 0.8).toInt()
        dialog.window?.setLayout(
            width,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun show() = dialog.show()
}
