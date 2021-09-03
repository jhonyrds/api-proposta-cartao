package br.com.cartao.proposta.controller

import br.com.cartao.proposta.model.BloqueiosCartao
import br.com.cartao.proposta.repository.CartaoRepository
import br.com.cartao.proposta.request.BloqueioCartaoRequest
import br.com.cartao.proposta.response.BloqueioCartaoResponse
import br.com.cartao.proposta.servicos.CartaoClient
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/cartao")
class BloqueioCartaoController(
        private val request: HttpServletRequest,
        private val cartaoRepository: CartaoRepository,
        private val cartaoClient: CartaoClient
) {

    private val LOGGER = LoggerFactory.getLogger(BloqueioCartaoController::class.java)

    @PostMapping("{cartaoId}/bloqueio")
    fun bloqueia(@PathVariable cartaoId: String, @RequestBody bloqueioRequest: BloqueioCartaoRequest): ResponseEntity<BloqueioCartaoResponse> {

        val cartao = cartaoRepository.findById(cartaoId)

        if (cartao.isPresent) {
            try {
                val response = cartaoClient.bloquear(cartaoId, bloqueioRequest)

                if (response.statusCode == HttpStatus.OK) {
                    cartao.get().bloqueios(BloqueiosCartao(request.remoteAddr, request.getHeader("User-Agent")))

                    cartaoRepository.save(cartao.get())

                    LOGGER.info("Adicionando bloqueio no cartãoId: $cartaoId")

                    return ResponseEntity.ok(BloqueioCartaoResponse())
                }
            } catch (e: Exception) {
                return ResponseEntity.unprocessableEntity().body(BloqueioCartaoResponse())
            }
        }
        return ResponseEntity.notFound().build()
    }
}