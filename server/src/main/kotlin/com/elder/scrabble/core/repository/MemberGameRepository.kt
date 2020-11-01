package com.elder.scrabble.core.repository

import com.elder.scrabble.core.models.MemberGame
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberGameRepository : CrudRepository<MemberGame, Int> {

    @Query("select mg.id, min(mg.score) from MemberGame mg group by mg.id")
    fun getWorst(): MemberGame
}
