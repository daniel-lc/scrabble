package com.elder.scrabble.core.models

import org.hibernate.annotations.JoinColumnOrFormula
import org.hibernate.annotations.JoinColumnsOrFormulas
import org.hibernate.annotations.JoinFormula
import javax.persistence.*

@Entity
@Table(name = "member_game")
data class MemberGame(
        @Id
        @Column(name = "member_game_id")
        val id: Int,
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "member_id")
        val member: Member,
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumn(name = "game_id")
        val game: Game,
        @Column(name = "game_result")
        @Enumerated(value = EnumType.STRING)
        val gameResult: GameResult,
        val score: Int,
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinColumnsOrFormulas(
                JoinColumnOrFormula(formula = JoinFormula(value = "(Select mg.member_id from member_game mg where mg.game_id = game_id and mg.member_id <> member_id)", referencedColumnName = "member_id"))
        )
        val rival: Member
)

enum class GameResult {
    WIN,
    LOSE,
    TIE
}

data class MemberGameDto(
        val memberGameID: Int,
        val gameResult: GameResult,
        val score: Int,
        val gameDto: GameDto,
        val rival: RivalDto
): Comparable<MemberGameDto> {

    object ModelMapper {
        fun from(memberGame: MemberGame): MemberGameDto = MemberGameDto(
                memberGame.id,
                memberGame.gameResult,
                memberGame.score,
                GameDto.ModelMapper.from(memberGame.game),
                RivalDto(memberGame.rival.id, memberGame.rival.name)
        )
    }

    override fun compareTo(other: MemberGameDto) = (score - other.score)
}
