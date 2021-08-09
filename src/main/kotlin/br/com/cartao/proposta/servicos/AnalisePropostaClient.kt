package br.com.cartao.proposta.servicos

import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(url = "\${analise.proposta}", name = "analise-de-proposta")
interface AnalisePropostaClient {
    @PostMapping("/solicitacao")
    fun analise(analiseRequest: AnaliseDePropostaRequest): AnalisePropostaResponse
}




