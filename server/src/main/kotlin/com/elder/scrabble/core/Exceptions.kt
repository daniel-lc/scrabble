package com.elder.scrabble.core

open class MemberNotExistsException(val memberId: Int, message: String?): RuntimeException(message)
