package com.caiquesenna.cadastrocliente.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.caiquesenna.cadastrocliente.model.Cliente
import com.caiquesenna.cadastrocliente.repository.ClienteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = ClienteRepository(application)

    //Lista de clientes que a Activity vai observar
    val clientes = MutableLiveData<List<Cliente>>()

    //Mensagem de feedback para o usuario
    val mensagem = MutableLiveData<String>()

    //Carrega todos os clientes do banco
    fun carregarClientes() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.listarClientes()
            }
            clientes.value = lista
        }
    }

    fun filtrarClientes(query: String) {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                if (query.isEmpty()) {
                    repository.listarClientes()
                } else {
                    repository.buscarClientes(query)
                }
            }
            clientes.value = lista
        }
    }

    //Busca CNPJ na API e salva se nao existir
    fun buscarCnpj(cnpj: String) {
        viewModelScope.launch {
            val response = repository.buscarCnpj(cnpj)
            if (response.isSuccessful) {
                val empresa = response.body()
                empresa?.let {
                    val novoCliente = Cliente(
                        tipo = it.tipo,
                        razao = it.nome, fantasia = it.fantasia,
                        cnpj = it.cnpj, inscricao = it.cnpj,
                        email = it.email, telefone = it.telefone,
                        cep = it.cep, logradouro = it.logradouro,
                        numero = it.numero, complemento = it.complemento,
                        bairro = it.bairro, cidade = it.municipio,
                        uf = it.uf,
                    )
                    withContext(Dispatchers.IO) {
                        val existente = repository.buscarPorCnpj(it.cnpj)
                        if (existente == null) {
                            repository.inserir(novoCliente)
                            mensagem.postValue("CLiente salvo: ${it.nome}")
                        } else {
                            mensagem.postValue("CNPJ ja cadastrado!")
                        }
                    }
                    carregarClientes()
                }
            }
        }
    }

    //Remove um cliente apos confirmacao
    fun deletar(cliente: Cliente) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.deletar(cliente) }
            carregarClientes()
            mensagem.value = "Cliente excluido"
        }
    }

}