package com.mauquoi.moneymanagement.moneymanagement.rest.constants

import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.PathVariable.ACCOUNT_ID

object URL {

    object AccountUrl {
        const val ACCOUNTS = "/accounts"
        const val ACCOUNT_BY_ID = "$ACCOUNTS/{$ACCOUNT_ID}"
    }

    object PathVariable {
        const val ACCOUNT_ID = "accountId"
    }
}