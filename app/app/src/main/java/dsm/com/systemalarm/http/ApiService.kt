package dsm.com.systemalarm.http

import dsm.com.systemalarm.vo.ComInfoVo
import dsm.com.systemalarm.vo.CpuInfoVo
import dsm.com.systemalarm.vo.MemInfoVo
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by mskan on 2018-04-12.
 */
interface ApiService {
    @get:GET("api/cpuInfo")
    val cpuInfo: Observable<ArrayList<CpuInfoVo>>

    @get:GET("api/comInfo")
    val comInfo: Observable<ComInfoVo>

    @get:GET("api/memInfo")
    val memInfo: Observable<ArrayList<MemInfoVo>>
}