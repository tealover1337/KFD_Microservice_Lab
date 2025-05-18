package kfd.cherkasov.apigateway.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties (
    val publicRsaFile: String
)