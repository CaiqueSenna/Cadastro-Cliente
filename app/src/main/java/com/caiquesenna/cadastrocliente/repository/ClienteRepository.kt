package com.caiquesenna.cadastrocliente.repository

import android.content.Context
import com.caiquesenna.cadastrocliente.api.RetrofitClient
import com.caiquesenna.cadastrocliente.data.AppDatabase
import com.caiquesenna.cadastrocliente.model.Cliente

class ClienteRepository(context: Context) {

    //Acessa o banco de dados room
    private val dao = AppDatabase.getDatabase(context).clienteDao()

    //Acessa a API de CNPJ
    private val api = RetrofitClient.api

    //Retorna todos os clientes salvos no banco
    suspend fun listarClientes(): List<Cliente> {
        return dao.listar()
    }

    suspend fun buscarClientes(query: String): List<Cliente> {
        return dao.buscar("%$query%")
    }

    //Buscar dados do CNPJ na API
    suspend fun buscarCnpj(cnpj: String) = api.buscarCnpj(cnpj)

    //Verificar se o CNPJ ja existe no banco
    suspend fun buscarPorCnpj(cnpj: String): Cliente?{
        return dao.buscarPorCnpj(cnpj)
    }

    //Insere um novo cliente
    suspend fun inserir(cliente: Cliente) = dao.inserir(cliente)

    //Atualizar um cliente existente
    suspend fun atualizar(cliente: Cliente) = dao.atualizar(cliente)

    //Remove um cliente
    suspend fun deletar(cliente: Cliente) = dao.deletar(cliente)
}