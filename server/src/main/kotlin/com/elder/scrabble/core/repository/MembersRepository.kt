package com.elder.scrabble.core.repository

import com.elder.scrabble.core.models.Member
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MembersRepository : CrudRepository<Member, Int> {


//    @Query("select m, max(mg.score), min(mg.score) from Member m join m._memberGames mg group by mg.id")
//    fun getWorstAndBest(): Collection<Member>

//    @Query("select mg.member, min(mg.score) from MemberGame mg group by mg.id")
//    fun getWorst(): Member


}
