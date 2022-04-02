package com.mauquoi.moneymanagement.moneymanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
class MoneyManagementApplication

fun main(args: Array<String>) {
    runApplication<MoneyManagementApplication>(*args)
}
