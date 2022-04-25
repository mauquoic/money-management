package com.mauquoi.moneymanagement.moneymanagement.stub

import com.mauquoi.moneymanagement.moneymanagement.domain.services.CryptoAssetService
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
class CryptoAssetStubController @Inject constructor(private val cryptoAssetService: CryptoAssetService){


}