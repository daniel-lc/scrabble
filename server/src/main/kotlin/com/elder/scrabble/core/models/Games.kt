package com.elder.scrabble.core.models

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "games")
data class Game(
        @Id
        @Column(name = "game_id")
        val id: Int,
        @Column(name = "played_at") val playedAt: Instant,
        @Column(name = "played_in") val playedIn: String
)

data class GameDto(
        val id: Int,
        val playedAt: String,
        val playedIn: String
) {

    object ModelMapper {
        fun from(game: Game): GameDto = GameDto(
                game.id,
                convertToDate(game.playedAt),
                game.playedIn
        )

        private fun convertToDate(joined: Instant): String = LocalDateTime.ofInstant(joined, ZoneOffset.UTC).toString() // todo: toto do dákeho util objectu
    }

}
