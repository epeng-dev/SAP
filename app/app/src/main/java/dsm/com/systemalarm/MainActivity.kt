package dsm.com.systemalarm

import android.app.FragmentManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import dsm.com.systemalarm.fragment.ComInfoFragment
import dsm.com.systemalarm.fragment.CpuInfoFragment
import dsm.com.systemalarm.fragment.MemInfoFragment
import dsm.com.systemalarm.viewpager.TabPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val comFragment = ComInfoFragment()
    private val cpuFragment = CpuInfoFragment()
    private val memFragment = MemInfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(activity_main_fragment_holder))
        val tabPagerAdapter = TabPagerAdapter(supportFragmentManager)
        activity_main_fragment_holder.adapter = tabPagerAdapter
        activity_main_fragment_holder.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        activity_main_fragment_holder.offscreenPageLimit = 0
    }

}
