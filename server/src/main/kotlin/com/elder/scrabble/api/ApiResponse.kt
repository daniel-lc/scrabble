package com.elder.scrabble.api;

import com.elder.scrabble.core.models.GameResult
import com.elder.scrabble.core.models.MemberBasicDetailsDto
import com.elder.scrabble.core.models.MemberDto
import com.elder.scrabble.core.models.MemberGameDto
import com.elder.scrabble.core.toOptional
import java.util.*

// TODO: move common properties to sealed class

data class Member(val id: Int, val name: String, val phoneNumber: String, val joined: String, val memberGames: Set<MemberGameDto>) {

    object ResponseMapper {
        fun from(memberDto: MemberDto): Member = Member(memberDto.id, memberDto.name, memberDto.phoneNumber, memberDto.joined, memberDto.memberGames)
    }
}

data class MemberRival(val id: Int, val name: String)

data class MemberBasicDetails(val id: Int, val name: String, val phoneNumber: String, val joined: String) {

    object ResponseMapper {
        fun from(memberBasicDetailsDto: MemberBasicDetailsDto): MemberBasicDetails = MemberBasicDetails(
                memberBasicDetailsDto.id, memberBasicDetailsDto.name, memberBasicDetailsDto.phoneNumber, memberBasicDetailsDto.joined
        )
    }
}

data class MemberDetail(val id: Int,
                        val name: String,
                        val wins: Int,
                        val loses: Int,
                        val averageScore: Float,
                        val highestScore: Optional<HighestScore>
) {

    object ResponseMapper {
        fun from(memberDto: MemberDto): MemberDetail = MemberDetail(
                memberDto.id,
                memberDto.name,
                wins(memberDto.memberGames),
                loses(memberDto.memberGames),
                avgScore(memberDto.memberGames),
                highestScore(memberDto.memberGames)
        )

        private fun wins(memberGames: Set<MemberGameDto>): Int = memberGames.filter { it.gameResult == GameResult.WIN }.count()

        private fun loses(memberGames: Set<MemberGameDto>): Int = memberGames.filter { it.gameResult == GameResult.LOSE }.count()

        private fun avgScore(memberGames: Set<MemberGameDto>): Float = memberGames.map { it.score }.average().toFloat()

        private fun highestScore(memberGames: Set<MemberGameDto>): Optional<HighestScore> = HighestScore.ResponseMapper.from(memberGames.max().toOptional())
    }
}

data class HighestScore(val highestScore: Int, val playedAt: String, val playedIn: String, val rival: MemberRival) {

    object ResponseMapper {
        fun from(memberGameDto: Optional<MemberGameDto>): Optional<HighestScore> = memberGameDto
                .map { HighestScore(it.score, it.gameDto.playedAt, it.gameDto.playedIn, MemberRival(it.rival.id, it.rival.name)) }
    }
}



