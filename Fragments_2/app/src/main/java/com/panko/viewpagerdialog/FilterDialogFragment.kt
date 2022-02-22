package com.panko.viewpagerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class FilterDialogFragment:DialogFragment(){
    private val articleTags = arrayOf(ArticleTag.NEWS.name,
            ArticleTag.POLITIC.name,
            ArticleTag.TECH.name)

    private val filterClickListener: FilterClickListener?
    get()= activity?.let { it as FilterClickListener }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity as AppActivity
        val checkedTags = activity.getBooleanArray()
        return AlertDialog.Builder(requireContext())
                .setTitle("Выберете категории")
                .setMultiChoiceItems(articleTags, checkedTags) {_,which: Int, isChecked: Boolean ->
                    checkedTags[which] = isChecked
                }
                .setNegativeButton("Cancel"){_,_->dialog?.dismiss() }
                .setPositiveButton("Accept"){_,_->
                    filterClickListener?.onSelectedItems(checkedTags)
                }
                .create()
    }
}