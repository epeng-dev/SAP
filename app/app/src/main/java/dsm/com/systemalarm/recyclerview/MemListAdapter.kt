package dsm.com.systemalarm.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dsm.com.systemalarm.R
import dsm.com.systemalarm.vo.MemInfoVo
import kotlinx.android.synthetic.main.item_mem.view.*

class MemListAdapter(val memList: ArrayList<MemInfoVo>) : RecyclerView.Adapter<MemListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_mem, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return memList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.memName.text = memList.get(position).diskInfo
        holder.memUsagePercent.text = (memList.get(position).usagePercent * 100).toInt().toString() + "%"
        holder.memUsedGigaByte.text = String.format("%.2f", memList.get(position).usedGigaBytes) + "GB"
        holder.memFreeGigaByte.text = String.format("%.2f", memList.get(position).freeGigaBytes) + "GB"
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val memName = itemView.item_mem_name
        val memUsagePercent = itemView.item_mem_usage_percent
        val memUsedGigaByte = itemView.item_mem_used_megabyte
        val memFreeGigaByte = itemView.item_mem_free_megabyte
    }
}