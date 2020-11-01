package com.elder.scrabble.api;

import java.util.*

class EmptyRequest {
    override fun toString(): String = "{}"
}

data class MemberDetailsRequest(val name: Optional<String>, val phoneNumber: Optional<String>)

object RequestPrinter {
    fun print(request: EmptyRequest): String = request.toString()
    fun print(request: MemberDetailsRequest): String = request.toString()
}
