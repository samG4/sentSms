package learning.shadow.com.sendotp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.sent_log_fragment.*
import learning.shadow.com.sendotp.adapter.SentSMSListAdapter
import learning.shadow.com.sendotp.model.Contact
import learning.shadow.com.sendotp.viewmodel.ContactViewModel

open class SentSMSFragment : Fragment() {

    val contactViewModel: ContactViewModel by lazy {
        ViewModelProviders.of(this).get(ContactViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.getParcelable<Contact>("CONTACT_DETAILS")?.let {
            it1 -> contactViewModel.insert(it1) }
        return inflater.inflate(R.layout.sent_log_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* savedInstanceState?.let {
            arguments?.getParcelable<Contact>("CONTACT_DETAILS")?.let {
                it1 -> contactViewModel.insert(it1) }*/
//        }
        rvSentItems?.layoutManager = LinearLayoutManager(context)
        val contactListAdapter = SentSMSListAdapter(
                this.context
        )
        rvSentItems?.adapter = contactListAdapter
        contactViewModel.allContacts.observe(this, object : Observer<List<Contact>>{
            override fun onChanged(t: List<Contact>?) {
                t?.let { contactListAdapter.setContact(it) }
            }

        })
    }
}
