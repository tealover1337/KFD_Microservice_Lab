package kfd.cherkasov.apigateway.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cors")
data class CorsProperties (
    val allowedOrigins: String = "*",
)