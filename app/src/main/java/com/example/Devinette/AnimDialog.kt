package com.example.Devinette

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.Devinette.R


class AnimDialog(private val context: Context) {
    private val dialog = Dialog(context)
    fun test(s: String) {
        Log.i("TAG", "test: $s")
    }
    init{

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    fun one(
            description: String,
            descriptiond: String,
            titleOfPositiveButton: String? = null,

            positiveButtonFunction: (() -> Unit)? = null,

    ) :Dialog{

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_view)


        val dialogDescription = dialog.findViewById(R.id.tv_FinishTime) as TextView
        val dialogDescriptiond3 = dialog.findViewById(R.id.tv_Tentatives) as TextView
        val dialogDescriptiond4 = dialog.findViewById(R.id.tv_Score) as TextView
        val dialogPositiveButton = dialog.findViewById(R.id.btnOk) as Button



        dialogDescription.text = description
        titleOfPositiveButton?.let { dialogPositiveButton.text = it } ?: dialogPositiveButton.text

        dialogPositiveButton.setOnClickListener {
            positiveButtonFunction?.invoke()
            dialog.dismiss()
        }



        return dialog

    }}