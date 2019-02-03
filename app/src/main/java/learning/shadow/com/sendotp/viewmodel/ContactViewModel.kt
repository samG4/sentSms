package learning.shadow.com.sendotp.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import learning.shadow.com.sendotp.model.Contact
import learning.shadow.com.sendotp.model.ContactDatabase
import learning.shadow.com.sendotp.repo.ContactRepository
import kotlin.coroutines.experimental.CoroutineContext

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ContactRepository
    val allContacts: LiveData<List<Contact>>

    init {
        val contactsDao = ContactDatabase.getDatabase(application, scope).contactDao()
        repository = ContactRepository(contactsDao)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact) = scope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}