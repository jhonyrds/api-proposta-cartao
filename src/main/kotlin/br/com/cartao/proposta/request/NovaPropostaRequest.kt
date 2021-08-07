package br.com.cartao.proposta.request

import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.shared.CPFOUCNPJ
import java.math.BigDecimal
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class NovaPropostaRequest(
    @field:CPFOUCNPJ @field:NotBlank val documento: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val nome: String,
    @field:NotBlank val endereco: String,
    @field:NotNull @field:Positive val salario: BigDecimal
) {
    fun toModel(propostaRepository: PropostaRepository): Proposta? {
        val possivelDocumento = propostaRepository.findByDocumento(documento)
        if (possivelDocumento.isEmpty) {
            return Proposta(documento, email, nome, endereco, salario)
        }
        return null
    }
}
