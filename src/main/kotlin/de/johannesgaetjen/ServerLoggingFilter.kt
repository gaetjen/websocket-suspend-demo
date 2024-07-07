package de.johannesgaetjen

import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.RequestFilter
import io.micronaut.http.annotation.ServerFilter
import io.micronaut.http.filter.FilterContinuation
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn

@ServerFilter("/**")
class ServerLoggingFilter {
    @RequestFilter
    @ExecuteOn(TaskExecutors.BLOCKING)
    fun filterRequest(
        request: HttpRequest<*>,
        continuation: FilterContinuation<MutableHttpResponse<*>>,
    ) {
        println("Request received: ${request.path}")
        continuation.proceed()
    }
}
