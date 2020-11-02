package com.filit.domain

class NotFoundException(throwable: Throwable) : ApiException(throwable)
class ServerException(throwable: Throwable) : ApiException(throwable)
class BadRequestException(throwable: Throwable) : ApiException(throwable)
class NetworkException(throwable: Throwable) : Exception(throwable)
class EmptyException : Exception()

open class ApiException : Exception {

    var errorMessage: String? = null
        private set

    constructor(detailMessage: String) : super(detailMessage)

    constructor(throwable: Throwable) : super(throwable) {
        if (throwable is ApiException) {
            this.errorMessage = throwable.errorMessage
        }
    }

    class Builder(private val detailMessage: String) {

        private var errorMessage: String? = null

        fun setErrorMessage(errorMessage: String?) {
            this.errorMessage = errorMessage
        }

        fun build() = ApiException(detailMessage).apply {
            this.errorMessage = this@Builder.errorMessage
        }
    }
}