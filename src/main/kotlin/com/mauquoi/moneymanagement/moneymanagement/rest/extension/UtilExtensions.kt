package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import java.util.*

fun <T> Optional<T>.toNullable(): T? = this.orElse(null)