package dsm.com.systemalarm.fragment

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
import dsm.com.systemalarm.recyclerview.CpuListAdapter
import dsm.com.systemalarm.vo.CpuInfoVo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cpu_info.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer


class CpuInfoFragment : Fragment() {
    private lateinit var apiService: ApiService
    private lateinit var cpuInfoUpdateTimer: Timer

    private var compositeDisposable = CompositeDisposable()
    private val cpuInfoList = ArrayList<CpuInfoVo>()
    private val adapter = CpuListAdapter(cpuInfoList)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_cpu_info, container, false)
        val retrofit = RetrofitClient.instance
        apiService = retrofit!!.create(ApiService::class.java)

        fetchData()
        initListView(view.recyclerview_cpuinfo)
        startCpuInfoUpdate()

        return view
    }

    override fun onStop() {
        super.onStop()
        stopCpuInfoUpdate()
    }


    private fun fetchData(){
        compositeDisposable.add(apiService.cpuInfo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    cpuInfo->updateData(cpuInfo)
                }
        )
    }

    private fun updateData(cpuList: ArrayList<CpuInfoVo>?) {
        this.cpuInfoList.clear()
        cpuList?.let { this.cpuInfoList.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    private fun initListView(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun startCpuInfoUpdate(){
        cpuInfoUpdateTimer = timer("CpuInfoUpdater", true, 10000, 10000, {fetchData()})
    }

    private fun stopCpuInfoUpdate() {
        cpuInfoUpdateTimer.cancel()
    }

    companion object {
        fun newInstance() = CpuInfoFragment()
    }
}
