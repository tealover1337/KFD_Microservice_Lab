package kfd.cherkasov.userservice.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import kfd.cherkasov.userservice.config.properties.JwtProperties

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Config