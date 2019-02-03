package learning.shadow.com.sendotp.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ContactDAO {
    @Query("SELECT * from contact_table ORDER BY sentTime ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Insert
    fun insert(contact: Contact)

}