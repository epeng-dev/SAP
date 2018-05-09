package dsm.com.systemalarm.vo

data class MemInfoVo(
        val diskInfo:String,
        val usagePercent:Double,
        val usedGigaBytes:Double,
        val freeGigaBytes:Double
)