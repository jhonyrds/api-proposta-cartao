package br.com.cartao.proposta.model

interface AcaoAposGerarProposta {
    fun executar(proposta: Proposta)
}