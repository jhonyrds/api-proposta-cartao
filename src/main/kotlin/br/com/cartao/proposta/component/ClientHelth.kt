package br.com.cartao.proposta.component


import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.net.Socket
import java.net.URL

@Component
class ClientHelth : HealthIndicator {

    @Value("\${analise.proposta}/solicitacao")
    private lateinit var url: String

    override fun health(): Health {
        try {
            Socket(URL(url).host, 9999)
        } catch (e: Exception) {
            return Health.down().withDetail("Erro", e.message).build()
        }
        return Health.up().build()
    }
}