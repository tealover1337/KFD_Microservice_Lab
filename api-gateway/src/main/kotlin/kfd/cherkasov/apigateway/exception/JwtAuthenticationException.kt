package kfd.cherkasov.apigateway.exception

class JwtAuthenticationException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)