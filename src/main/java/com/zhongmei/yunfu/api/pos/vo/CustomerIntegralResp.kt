package com.zhongmei.yunfu.api.pos.vo

class CustomerIntegralResp {

    var aggregateCount: Int? = null
    var integralCount: Int? = null
    var items: List<NewIntegralRecord>? = null

    class NewIntegralRecord {
        var id: Long? = null
        var recordType: Int? = null //记录类型1储值、2消费
        var beforeIntegral: Int? = null
        var addIntegral: Int? = null
        var endIntegral: Int? = null
        var createDateTime: Long? = null
        var modifyDateTime: Long? = null
        var status: Int? = null
        var reason: String? = null
        var userName: String? = null
    }
}
