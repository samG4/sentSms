package learning.shadow.com.sendotp.repo

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import learning.shadow.com.sendotp.model.Contact
import learning.shadow.com.sendotp.model.ContactDAO

class ContactRepository(private val contactDAO: ContactDAO) {

    val allContacts: LiveData<List<Contact>> = contactDAO.getAllContacts()

    @WorkerThread
    fun insert(contact: Contact) {
        contactDAO.insert(contact)
    }
}