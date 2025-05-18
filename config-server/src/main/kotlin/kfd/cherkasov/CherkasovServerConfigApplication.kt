package kfd.cherkasov

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class CherkasovServerConfigApplication

fun main(args: Array<String>) {
    runApplication<CherkasovServerConfigApplication>(*args)
}
