package learning.shadow.com.sendotp.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import learning.shadow.com.sendotp.ContactListFragment
import learning.shadow.com.sendotp.SentSMSFragment

class PageAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    private val title = arrayOf("Contact List", "SMS Log")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                ContactListFragment()
            }
            1->{
                SentSMSFragment()
            }
            else->{
                Fragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}