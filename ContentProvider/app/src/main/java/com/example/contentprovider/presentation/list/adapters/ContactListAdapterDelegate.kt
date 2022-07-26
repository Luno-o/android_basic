package com.example.contentprovider.presentation.list.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentprovider.R
import com.example.contentprovider.data.Contact
import com.example.contentprovider.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_contact_list.view.*
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactListAdapterDelegate(private val onItemClick: (contact: Contact) -> Unit):
    AbsListItemAdapterDelegate<Contact,Contact,ContactListAdapterDelegate.Holder>() {


    override fun isForViewType(item: Contact, items: MutableList<Contact>, position: Int): Boolean {
       return item is Contact
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_contact),onItemClick)
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
    class Holder(override val containerView: View,val onItemClick: (contact: Contact) -> Unit)
        : RecyclerView.ViewHolder(containerView),LayoutContainer{

fun bind(contact: Contact){
    containerView.setOnClickListener {
    onItemClick(contact)
    }
containerView.nameTextView.text = contact.name
    if (contact.phones.isNotEmpty()){
containerView.phoneTextView.text = contact.phones[0]
    }else{
        containerView.phoneTextView.text = ""
    }

}
    }
}