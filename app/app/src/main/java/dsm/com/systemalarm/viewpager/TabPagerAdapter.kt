package dsm.com.systemalarm.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import dsm.com.systemalarm.fragment.ComInfoFragment
import dsm.com.systemalarm.fragment.CpuInfoFragment
import dsm.com.systemalarm.fragment.MemInfoFragment

class TabPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = ComInfoFragment.newInstance()
                fragment
            }
            1 -> {
                val fragment = CpuInfoFragment.newInstance()
                fragment
            }
            else -> {
                val fragment = MemInfoFragment.newInstance()
                fragment
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}