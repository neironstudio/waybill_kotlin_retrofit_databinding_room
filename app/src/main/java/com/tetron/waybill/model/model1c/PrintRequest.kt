package com.tetron.waybill.model.model1c

import com.google.gson.annotations.SerializedName

class PrintRequest(_waybillDocId: String, _userId: String) {

    @SerializedName("WaybillId")
    var waybillDocId: String? = _waybillDocId

    @SerializedName("UserId")
    var userId: String? = _userId

}