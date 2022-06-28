package com.mauquoi.moneymanagement.moneymanagement.rest.constants

import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.PathVariable.ACCOUNT_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.PathVariable.CRYPTO_ID

object URL {

    object AccountUrl {
        const val ACCOUNTS = "/accounts"
        const val ACCOUNT_BY_ID = "$ACCOUNTS/{$ACCOUNT_ID}"
    }

    object CryptoUrl {
        const val CRYPTOS = "/cryptos"
        const val CRYPTO_BY_ID = "$CRYPTOS/{$CRYPTO_ID}"
    }

    object CryptoAssetUrl {
        const val CRYPTO_ASSETS = "/cryptos/assets"
        const val CRYPTO_ASSET_BY_ID = "$CRYPTO_ASSETS/{$CRYPTO_ID}"
    }

    object PathVariable {
        const val ACCOUNT_ID = "accountId"
        const val CRYPTO_ID = "cryptoId"
    }
}