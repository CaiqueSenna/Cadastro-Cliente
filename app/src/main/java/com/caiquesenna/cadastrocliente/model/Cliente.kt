package com.caiquesenna.cadastrocliente.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,
    val razao: String,
    val fantasia: String,
    val cnpj: String,
    val inscricao: String,
    val email: String,
    val telefone: String,
    val cep: String,
    val logradouro: String,
    val numero: String,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val uf: String
) : Serializable
