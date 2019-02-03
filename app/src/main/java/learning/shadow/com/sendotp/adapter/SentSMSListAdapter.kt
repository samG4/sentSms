package learning.shadow.com.sendotp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.sent_sms.view.*
import learning.shadow.com.sendotp.R
import learning.shadow.com.sendotp.model.Contact

class SentSMSListAdapter(context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sentItemList =  emptyList<Contact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(inflater.inflate(R.layout.sent_sms, parent, false))
    }

    override fun getItemCount() = sentItemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContactViewHolder){
            holder.name.text = (sentItemList[position].first_name+" "+sentItemList[position].last_name)
            holder.otp.text = sentItemList[position].OTP.toString()
            holder.time.text = sentItemList[position].sentTime
        }
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.tvName
        var otp = itemView.tvOTP
        var time = itemView.tvTime
    }

    internal fun setContact(contact: List<Contact>) {
        this.sentItemList = contact
        notifyDataSetChanged()
    }
}
