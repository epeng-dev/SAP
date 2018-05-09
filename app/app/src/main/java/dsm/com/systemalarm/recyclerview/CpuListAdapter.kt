package dsm.com.systemalarm.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dsm.com.systemalarm.R
import dsm.com.systemalarm.vo.CpuInfoVo
import kotlinx.android.synthetic.main.item_cpu.view.*
import kotlin.math.roundToInt

/**
 * Created by mskan on 2018-04-12.
 */
class CpuListAdapter (val cpuList: ArrayList<CpuInfoVo>)
    : RecyclerView.Adapter<CpuListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cpu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cpuNum.text = "CPU " + cpuList.get(position).cpuNum.toString()
        holder.cpuTotal.text = (cpuList.get(position).cpuTotal*100).roundToInt().toString() + "%"
        holder.cpuSystem.text = (cpuList.get(position).cpuSystem * 100).roundToInt().toString() + "%"
        holder.cpuNice.text = (cpuList.get(position).cpuNice * 100).roundToInt().toString() + "%"
        holder.cpuIdle.text = (cpuList.get(position).cpuIdle * 100).roundToInt().toString() + "%"
    }

    override fun getItemCount(): Int {
        return cpuList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cpuNum = view.item_cpu_num
        var cpuTotal = view.item_cpu_total
        var cpuSystem = view.item_cpu_sys
        var cpuNice = view.item_cpu_nice
        var cpuIdle = view.item_cpu_idle
    }
}