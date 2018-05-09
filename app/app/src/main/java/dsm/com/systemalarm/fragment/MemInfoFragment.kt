package dsm.com.systemalarm.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dsm.com.systemalarm.R
import dsm.com.systemalarm.http.ApiService
import dsm.com.systemalarm.http.RetrofitClient
import dsm.com.systemalarm.recyclerview.MemListAdapter
import dsm.com.systemalarm.vo.CpuInfoVo
import dsm.com.systemalarm.vo.MemInfoVo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cpu_info.view.*
import kotlinx.android.synthetic.main.fragment_mem_info.view.*
import java.util.*
import kotlin.concurrent.timer

class MemInfoFragment : Fragment() {
    internal lateinit var apiService: ApiService
    internal lateinit var memInfoUpdateTimer: Timer

    internal var compositeDisposable = CompositeDisposable()
    internal val memInfoList = ArrayList<MemInfoVo>()
    internal val adapter = MemListAdapter(memInfoList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mem_info, container, false)
        val retrofit = RetrofitClient.instance
        apiService = retrofit!!.create(ApiService::class.java)

        fetchData()
        initListView(view.recyclerview_meminfo)
        startCpuInfoUpdate()

        return view
    }

    override fun onStop() {
        super.onStop()
        stopCpuInfoUpdate()
    }


    private fun fetchData(){
        compositeDisposable.add(apiService.memInfo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    memInfo->updateData(memInfo)
                }
        )
    }

    private fun updateData(memList: ArrayList<MemInfoVo>?) {
        this.memInfoList.clear()
        memList?.let { this.memInfoList.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    private fun initListView(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun startCpuInfoUpdate(){
        memInfoUpdateTimer = timer("CpuInfoUpdater", true, 10000, 10000, {fetchData()})
    }

    private fun stopCpuInfoUpdate() {
        memInfoUpdateTimer.cancel()
    }

    companion object {
        fun newInstance() = MemInfoFragment()
    }

}
