package com.caiquesenna.cadastrocliente.model

data class ReceitaResponse(
    val abertura: String,
    val bairro: String,
    val cep: String,
    val cnpj: String,
    val complemento: String,
    val data_situacao: String,
    val email: String,
    val fantasia: String,
    val logradouro: String,
    val municipio: String,
    val natureza_juridica: String,
    val nome: String,
    val numero: String,
    val status: String,
    val telefone: String,
    val tipo: String,
    val uf: String,
    val ultima_atualizacao: String
)