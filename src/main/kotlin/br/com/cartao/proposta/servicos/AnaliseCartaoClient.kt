package br.com.cartao.proposta.servicos

import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(url = "\${analise.cartao}", name = "cartao")
interface AnaliseCartaoClient {

    @PostMapping
    fun gerar(cartaoRequest: AnaliseDePropostaRequest): AnaliseCartaoResponse
}