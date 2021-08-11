package br.com.cartao.proposta.repository

import br.com.cartao.proposta.model.Cartao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartaoRepository: JpaRepository<Cartao, String>