package com.elder.scrabble.api.handler;

import com.elder.scrabble.api.*
import com.elder.scrabble.api.adapter.ResponseApiAdapter.ErrorResponse
import com.elder.scrabble.api.adapter.ResponseApiAdapter.Response
import com.elder.scrabble.api.adapter.ResponseApiAdapter.Response.ClientFailure
import com.elder.scrabble.api.adapter.ResponseApiAdapter.Response.Companion.successResponse
import com.elder.scrabble.core.MemberNotExistsException
import com.elder.scrabble.core.logOnError
import com.elder.scrabble.core.models.MemberBasicDetailsDto
import com.elder.scrabble.core.models.MemberDto
import com.elder.scrabble.core.service.MembersService
import org.slf4j.Logger
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.kotlin.core.publisher.toMono

@Component
class MembersApiHandler(private val membersService: MembersService, val logger: Logger) {

    fun getMembers(request: ServerRequest, emptyRequest: EmptyRequest): Mono<Response> = membersService
            .getMembers()
            .collectList()
            .map { buildSuccess(it) }

    fun getMember(request: ServerRequest, emptyRequest: EmptyRequest): Mono<Response> = request
            .pathVariable("member_id").toMono()
            .flatMap { membersService.getMember(it.toInt()) }
            .map { buildSuccess(it) }
            .logOnError(NumberFormatException::class.java) { logger.debug(it.message) }
            .onErrorResume(NumberFormatException::class.java) { ClientFailure(ErrorResponse(it.message)).toMono() }
            .logOnError(MemberNotExistsException::class.java) { logger.debug(it.message) }
            .onErrorResume(MemberNotExistsException::class.java) { ClientFailure(ErrorResponse("Member with id: ${it.memberId} not exists")).toMono() }

    fun updateMember(request: ServerRequest, memberDetailsRequest: MemberDetailsRequest): Mono<Response> = request
            .pathVariable("member_id").toMono()
            .flatMap { membersService.updateMemberDetails(it.toInt(), memberDetailsRequest) }
            .map { buildSuccess(it) }
            .logOnError(NumberFormatException::class.java) { logger.debug(it.message) }
            .onErrorResume(NumberFormatException::class.java) { ClientFailure(ErrorResponse(it.message)).toMono() }
            .logOnError(MemberNotExistsException::class.java) { logger.debug(it.message) }
            .onErrorResume(MemberNotExistsException::class.java) { ClientFailure(ErrorResponse("Member with id: ${it.memberId} not exists")).toMono() }

    // todo: these success builders could be some generalize

    private fun buildSuccess(memberDto: MemberBasicDetailsDto): Response = successResponse(MemberBasicDetails.ResponseMapper.from(memberDto))

    private fun buildSuccess(memberDto: MemberDto): Response = successResponse(MemberDetail.ResponseMapper.from(memberDto))

    private fun buildSuccess(memberDtos: List<MemberDto>): Response = successResponse(memberDtos.map { Member.ResponseMapper.from(it) })
}
