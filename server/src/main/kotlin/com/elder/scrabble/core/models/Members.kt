package com.elder.scrabble.core.models

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "members")
data class Member(
        @Id
        @Column(name = "member_id")
        val id: Int,
        val name: String,
        @Column(name = "phone_number")  val phoneNumber: String,
        @Column(name = "joined_at") val joined: Instant
) {
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    private val _memberGames = mutableSetOf<MemberGame>()

    val memberGames get() = _memberGames.toList()

}

data class MemberDto(
        val id: Int,
        val name: String,
        val phoneNumber: String,
        val joined: String,
        val memberGames: Set<MemberGameDto>
) {

    object ModelMapper {
        fun from(member: Member): MemberDto = MemberDto(
                member.id,
                member.name,
                member.phoneNumber,
                convertToDate(member.joined),
                member.memberGames.map { MemberGameDto.ModelMapper.from(it) }.toSet()
        )

        private fun convertToDate(joined: Instant): String = LocalDateTime.ofInstant(joined, ZoneOffset.UTC).toString()
    }

}

data class MemberBasicDetailsDto(val id: Int,
                            val name: String,
                            val phoneNumber: String,
                            val joined: String) {

    object ModelMapper {
        fun from(member: Member): MemberBasicDetailsDto = MemberBasicDetailsDto(
                member.id,
                member.name,
                member.phoneNumber,
                convertToDate(member.joined)
        )

        private fun convertToDate(joined: Instant): String = LocalDateTime.ofInstant(joined, ZoneOffset.UTC).toString()
    }
}

data class RivalDto(val id: Int, val name: String)

