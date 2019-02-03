package learning.shadow.com.sendotp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.contact_item.view.*
import learning.shadow.com.sendotp.R
import learning.shadow.com.sendotp.model.Contact

class ContactListAdapter(val context: Context?, val adapterCallback: AdapterCallback, val contactList: List<Contact>?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactItemHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.contact_item,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return contactList?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContactItemHolder){
            contactList?.get(position)?.let { holder.bindView(it) }
        }
    }

    private inner class ContactItemHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val name = itemView.tvName
        private val number = itemView.tvNumber

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(item: Contact) {
            name.text = (item.first_name + " " + item.last_name)
            number.text = item.number
        }

        override fun onClick(v: View?) {
            contactList?.get(adapterPosition)?.let { adapterCallback.itemTapped(it) }
        }
    }

    interface AdapterCallback{
        fun itemTapped(item: Contact)
    }

}