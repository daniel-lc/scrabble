package com.elder.scrabble.core.service

import com.elder.scrabble.core.models.MemberDto
import com.elder.scrabble.core.models.MemberGameDto
import com.elder.scrabble.core.repository.MemberGameRepository
import com.elder.scrabble.core.repository.MembersRepository
import com.elder.scrabble.core.toOptional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.util.function.Tuple2
import reactor.util.function.Tuples

@Service
class BoardService(private val membersRepository: MembersRepository, private val memberGameRepository: MemberGameRepository) {

      // Todo: this need to be reworked to getting directly only worst and best members by query
//      fun getWorstAndBest(): Mono<Tuple2<MemberDto, MemberDto>> = Flux.defer { membersRepository.findAll().toFlux() }
//              .map { MemberDto.ModelMapper.from(it) }
//              .collectList()
//              .map { Tuples.of(min(it), max(it)) }


      //private fun min(members: List<MemberDto>): MemberDto = members.reduce { acc, memberDto -> acc.memberGames.max() }

      //private fun max(members: List<MemberDto>): MemberDto =
}
