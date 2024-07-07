package de.johannesgaetjen

import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.PathVariable
import io.micronaut.websocket.CloseReason
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnError
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import jakarta.inject.Singleton


@Suppress("MnUnresolvedPathVariable")
@ServerWebSocket("/blocking/{clientId}")
class BlockingWebsocketController(
    private val onOpenCounter: BlockingOnOpenCounter
) {
    @OnOpen
    fun onOpen(
        @PathVariable("clientId")
        clientId: String,
        httpRequest: HttpRequest<*>,
        webSocketSession: WebSocketSession,
    ) {
        println("onOpen blocking, clientId: $clientId")
        onOpenCounter.increment()
    }

    @OnMessage
    fun onMessage(
        @PathVariable("clientId")
        clientId: String,
        message: String,
        webSocketSession: WebSocketSession,
    ) {

    }


    @OnClose
    fun onClose(
        @PathVariable("clientId")
        clientId: String,
        closeReason: CloseReason,
        webSocketSession: WebSocketSession,
    ) {
    }

    @OnError
    fun onError(
        @PathVariable("clientId")
        clientId: String,
        throwable: Throwable,
        webSocketSession: WebSocketSession,
    ) {

    }
}
