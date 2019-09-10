package com.tetron.waybill.view.dialog

class DialogInputTime(
    title: String,
    warningMsg: String?,
    alarmMsg: String?
) : SimpleAlertDialog(SimpleAlertDialog.TypeDialog.EDIT_TIME.name, title, warningMsg, alarmMsg)