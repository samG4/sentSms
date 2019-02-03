package learning.shadow.com.sendotp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.contact_list.*
import learning.shadow.com.sendotp.adapter.ContactListAdapter
import learning.shadow.com.sendotp.model.Contact
import java.io.IOException


class ContactListFragment : Fragment(), ContactListAdapter.AdapterCallback {
    override fun itemTapped(item: Contact) {
        val intent = Intent(this.activity, Message::class.java)
        intent.putExtra("CONTACT_ITEM", item)
        startActivity(intent)
    }

    private val contactList: List<Contact>?
        get() {
            val jsonString = getAssetsJSON("contacts.json")
            val listType = object : TypeToken<List<Contact>>() {
            }.type
            return Gson().fromJson<List<Contact>>(jsonString, listType)
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvContactList?.layoutManager = LinearLayoutManager(context)
        val contactListAdapter = ContactListAdapter(
                this.context,
                this,
                contactList
        )
        rvContactList?.adapter = contactListAdapter
    }

    private fun getAssetsJSON(tocItemViewJson: String): String? {
        var json: String? = null
        try {
            val inputStream = this.activity?.assets?.open(tocItemViewJson)
            val size = inputStream?.available()?:0
            val buffer = ByteArray(size)
            inputStream?.read(buffer)
            inputStream?.close()
            json = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }


}
