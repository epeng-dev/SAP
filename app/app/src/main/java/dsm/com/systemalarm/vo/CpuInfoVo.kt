package dsm.com.systemalarm.vo

/**
 * Created by mskan on 2018-04-12.
 */
data class CpuInfoVo(
        val cpuNum: Int,
        val cpuTotal: Double,
        val cpuSystem: Double,
        val cpuNice: Double,
        val cpuIdle: Double
)
