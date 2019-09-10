package com.tetron.waybill.view.dialog

class DialogInputString(
    title: String,
    warningMsg: String?,
    alarmMsg: String?
) : SimpleAlertDialog(SimpleAlertDialog.TypeDialog.EDIT_TEXT.name, title, warningMsg, alarmMsg)