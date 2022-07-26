package com.example.contentprovider.presentation.list.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.contentprovider.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactListAdapter(onItemClick: (contact: Contact) -> Unit): AsyncListDifferDelegationAdapter<Contact>(ContactDiffUtilCallback()) {
init {
    delegatesManager.addDelegate(ContactListAdapterDelegate(onItemClick))
}
    class ContactDiffUtilCallback : DiffUtil.ItemCallback<Contact>(){
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
return when{
oldItem is Contact && newItem is Contact -> oldItem.id == newItem.id
    else -> false
}
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
       return     oldItem == newItem
        }

    }
}