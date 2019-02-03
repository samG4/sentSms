package learning.shadow.com.sendotp

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import learning.shadow.com.sendotp.adapter.PageAdapter

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: PageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = PageAdapter(supportFragmentManager, this)
        viewPager.adapter = mSectionsPagerAdapter
        (tabs  as TabLayout).setupWithViewPager(viewPager)
    }

    override fun onResume() {
        super.onResume()
        if(intent.extras!=null)
        {
//            val contact: Contact = intent.extras.get("CONTACT_DETAILS") as Contact
//            SentSMSFragment().contactViewModel.insert(contact)
        }


    }
}
