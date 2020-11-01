package com.elder.scrabble.api.handler

import com.elder.scrabble.api.EmptyRequest
import com.elder.scrabble.api.Member
import com.elder.scrabble.api.MemberDetail
import com.elder.scrabble.api.adapter.ResponseApiAdapter.Response
import com.elder.scrabble.core.models.MemberDto
import com.elder.scrabble.core.service.BoardService
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono

@Component
class BoardApiHandler(private val boardService: BoardService, private val logger: Logger) {

    //fun getLeaders(serverRequest: ServerRequest, emptyRequest: EmptyRequest): Mono<Response> = boardService

//    fun getWorstAndBest(serverRequest: ServerRequest, emptyRequest: EmptyRequest): Mono<Response> = boardService
//            .getWorstAndBest()
//            .map { buildSuccess(it) }

    private fun buildSuccess(memberDtos: List<MemberDto>): Response = Response.successResponse(memberDtos.map { Member.ResponseMapper.from(it) })

    private fun buildSuccess(memberDto: MemberDto): Response = Response.successResponse(MemberDetail.ResponseMapper.from(memberDto))
}
