package learning.shadow.com.sendotp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_message.*
import learning.shadow.com.sendotp.model.Contact
import java.text.SimpleDateFormat
import java.util.*

class Message : AppCompatActivity(), View.OnClickListener, SendMessageWorker.SmsSentReceipt {

    private val contactDetails: Contact by lazy {
        intent.extras["CONTACT_ITEM"] as Contact
    }

    private val otp : Int by lazy {
        Random().nextInt(99999)
    }

    companion object {
        val API_KEY = "2s6ZooOvtS8-LU0tGYSwJaIgY8PWRNPeoxpPFlsFCa"
        val SENDER = "KISAAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        imgSend.setOnClickListener(this)
        imgClose.setOnClickListener(this)
        tvName.text = (contactDetails.first_name + " " + contactDetails.last_name)
        tvNumber.text = contactDetails.number
        etMessage.setText((getString(R.string.default_text) + otp))

    }

    override fun onClick(v: View?) {
        when (v) {
            imgSend -> {
                try {
                    val sms = SendMessageWorker(API_KEY,tvNumber.text.toString(),etMessage.text.toString(), SENDER, this, this)
                    sms.sendSMS()
                } catch (e: Exception) {
                    Log.e("MESSAGE","${e.printStackTrace()}",e)
                }
            }
            imgClose -> {
                finish()
            }
        }
    }

    override fun smsSentStatus(status: Boolean) {
        if(status){
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            contactDetails.sentTime = sdf.format(Date())
            contactDetails.OTP = otp
            val bundle = Bundle()
            bundle.putParcelable("CONTACT_DETAILS", contactDetails)
            val sentFragment = SentSMSFragment()
            sentFragment.onSaveInstanceState(bundle)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.messageRoot, sentFragment, sentFragment::javaClass.name).addToBackStack(null).commit();
        }
    }
}

