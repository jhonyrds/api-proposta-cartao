package br.com.cartao.proposta.repository

import br.com.cartao.proposta.model.Biometria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BiometriaRepository: JpaRepository<Biometria, String>