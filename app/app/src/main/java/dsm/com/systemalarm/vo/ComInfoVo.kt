package dsm.com.systemalarm.vo

object ComInfoVo {
    data class Result(
            val cpu: Cpu,
            val ram: Ram,
            val network: Network
    )
    data class Cpu(val total: Double)
    data class Ram(val used: Double)
    data class Network(val rx: Int, val tx: Int)
}