package com.elder.scrabble.core.service

import com.elder.scrabble.api.MemberDetailsRequest
import com.elder.scrabble.core.MemberNotExistsException
import com.elder.scrabble.core.models.Member
import com.elder.scrabble.core.models.MemberBasicDetailsDto
import com.elder.scrabble.core.models.MemberDto
import com.elder.scrabble.core.repository.MembersRepository
import com.elder.scrabble.core.toMonoOrEmpty
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Service
class MembersService(val membersRepository: MembersRepository) {

    fun getMembers(): Flux<MemberDto> = Flux.defer { membersRepository.findAll().toFlux() }
            .map { MemberDto.ModelMapper.from(it) }

    fun getMember(memberId: Int): Mono<MemberDto> = Mono.defer { membersRepository.findById(memberId).toMonoOrEmpty() }
            .map { MemberDto.ModelMapper.from(it) }
            .switchIfEmpty(MemberNotExistsException(memberId, "Member with id: $memberId not found.").toMono())

    fun updateMemberDetails(memberId: Int, request: MemberDetailsRequest): Mono<MemberBasicDetailsDto> = Mono
            .defer { membersRepository.findById(memberId).toMonoOrEmpty() }
            .map { membersRepository.save(
                    Member(it.id, request.name.orElse(it.name), request.phoneNumber.orElse(it.phoneNumber), it.joined))
            }
            .switchIfEmpty(MemberNotExistsException(memberId, "Member with id: $memberId not found.").toMono())
            .map { MemberBasicDetailsDto.ModelMapper.from(it) }
}
