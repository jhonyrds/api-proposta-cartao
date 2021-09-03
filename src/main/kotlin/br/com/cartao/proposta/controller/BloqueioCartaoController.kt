package br.com.cartao.proposta.controller

import br.com.cartao.proposta.model.BloqueiosCartao
import br.com.cartao.proposta.repository.CartaoRepository
import br.com.cartao.proposta.response.BloqueioCartaoResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/cartao")
class BloqueioCartaoController(
        private val request: HttpServletRequest,
        private val cartaoRepository: CartaoRepository
) {

    private val LOGGER = LoggerFactory.getLogger(BloqueioCartaoController::class.java)

    @PostMapping("{cartaoId}/bloqueio")
    fun bloqueia(@PathVariable cartaoId: String): ResponseEntity<BloqueioCartaoResponse> {

        val cartao = cartaoRepository.findById(cartaoId)

        if (cartao.isPresent) {
            return if (cartao.get().bloqueios.size == 0) {
                cartao.get().bloqueios(BloqueiosCartao(request.remoteAddr, request.getHeader("User-Agent")))

                cartaoRepository.save(cartao.get())

                LOGGER.info("Adicionando bloqueio no cartãoId: $cartaoId")

                ResponseEntity.ok(BloqueioCartaoResponse())
            } else {
                ResponseEntity.unprocessableEntity().body(BloqueioCartaoResponse())
            }
        }
        return ResponseEntity.notFound().build()
    }
}



