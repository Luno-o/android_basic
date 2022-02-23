package com.panko.viewpagerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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
    get()= parentFragment?.let { it as FilterClickListener }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val fragment = parentFragment as ViewpagerFragment
        val checkedTags = fragment.getBooleanArray()
        val checkedFilterTag = checkedTags.copyOf()
        return AlertDialog.Builder(requireContext())
                .setTitle("Выберете категории")
                .setMultiChoiceItems(articleTags, checkedFilterTag) {_,which: Int, isChecked: Boolean ->
                    checkedFilterTag[which] = isChecked
                }
                .setNegativeButton("Cancel"){_,_->dialog?.dismiss() }
                .setPositiveButton("Accept"){_,_->
                    if (checkedFilterTag.contentEquals(checkedTags)) dialog?.dismiss()
                    else filterClickListener?.onSelectedItems(checkedFilterTag)
                }
                .create()
    }
}