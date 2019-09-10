package com.tetron.waybill.view.dialog

class DialogInputInt(
    title: String,
    warningMsg: String?,
    alarmMsg: String?,
    inputDigitLength: Int
) : SimpleAlertDialog(SimpleAlertDialog.TypeDialog.EDIT_TEXT_INT.name, title, warningMsg, alarmMsg, inputDigitLength)