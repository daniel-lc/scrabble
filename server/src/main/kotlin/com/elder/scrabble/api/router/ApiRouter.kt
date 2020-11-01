package com.elder.scrabble.api.router;

import com.elder.scrabble.api.RequestPrinter
import com.elder.scrabble.api.adapter.ResponseApiAdapter
import com.elder.scrabble.api.handler.BoardApiHandler
import com.elder.scrabble.api.handler.MembersApiHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import kotlin.reflect.KClass


@Configuration
class ApiRouter(
        private val membersApiHandler: MembersApiHandler,
        private val boardApiHandler: BoardApiHandler,
        private val responseApiAdapter: ResponseApiAdapter
) {

    @Bean
    fun router(): RouterFunction<ServerResponse> {
        return router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/api/members", membersApiHandler::getMembers.adapt(MembersApiHandler::class, RequestPrinter::print))
                GET("/api/members/{member_id}", membersApiHandler::getMember.adapt(MembersApiHandler::class, RequestPrinter::print))
                PUT("/api/members/{member_id}", membersApiHandler::updateMember.adapt(MembersApiHandler::class, RequestPrinter::print))
                //GET("/api/board/leaders", boardApiHandler::getLeaders.adapt(BoardApiHandler::class, RequestPrinter::print))
                //GET("/api/board/worstBest", boardApiHandler::getWorstAndBest.adapt(BoardApiHandler::class, RequestPrinter::print))
            }
        }
    }

    private inline fun <reified T> ((ServerRequest, T) -> Mono<ResponseApiAdapter.Response>).adapt(handlerClass: KClass<*>, noinline reqPrinter: (T) -> String) =
            responseApiAdapter.adapt(this, T::class.java, handlerClass, reqPrinter, LoggerFactory.getLogger(handlerClass.java))

}
