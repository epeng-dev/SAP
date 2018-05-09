package dsm.com.systemalarm.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dsm.com.systemalarm.R

class ComInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_com_info, container, false)
    }

    companion object {
        fun newInstance() = ComInfoFragment()
    }
}// Required empty public constructor
