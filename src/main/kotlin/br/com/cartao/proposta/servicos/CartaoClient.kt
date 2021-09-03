package br.com.cartao.proposta.servicos

import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.request.BloqueioCartaoRequest
import br.com.cartao.proposta.response.BloqueioCartaoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(url = "\${analise.cartao}", name = "cartao")
interface CartaoClient {

    @PostMapping
    fun gerar(cartaoRequest: AnaliseDePropostaRequest): AnaliseCartaoResponse

    @PostMapping("{cartaoId}/bloqueios")
    fun bloquear(@PathVariable cartaoId: String, @RequestBody bloqueioCartaoRequest: BloqueioCartaoRequest): ResponseEntity<BloqueioCartaoResponse>
}