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

class CadastroViewModel(application: Application): AndroidViewModel(application) {
    private val repository = ClienteRepository(application)

    //Indica quando a atualizacao foi concluida (para fechar a tela)
    val atualizado = MutableLiveData<Boolean>()

    //Mensagem de erro de validacao
    val erro = MutableLiveData<String>()

    //Dados da empresa buscada via CNPJ
    val dadosCnpj = MutableLiveData<com.caiquesenna.cadastrocliente.model.ReceitaResponse?>()

    fun buscarCnpj(cnpj: String) {
        viewModelScope.launch {
            try {
                val response = repository.buscarCnpj(cnpj)
                if (response.isSuccessful) {
                    dadosCnpj.value = response.body()
                } else {
                    erro.value = "Erro ao buscar CNPJ"
                }
            } catch (e: Exception) {
                erro.value = "Falha na conexão"
            }
        }
    }

    fun salvar(
        id: Int = 0,
        tipo: String,
        razao: String,
        fantasia: String,
        cnpj: String,
        inscricao: String,
        email: String,
        telefone: String,
        cep: String,
        logradouro: String,
        numero: String,
        complemento: String,
        bairro: String,
        cidade: String,
        uf: String
    ) {
        if (razao.isEmpty() || cnpj.isEmpty()) {
            erro.value = "Razão Social e CNPJ/CPF são obrigatórios"
            return
        }

        val novoCliente = Cliente(
            id = id,
            tipo = tipo,
            razao = razao,
            fantasia = fantasia,
            cnpj = cnpj,
            inscricao = inscricao,
            email = email,
            telefone = telefone,
            cep = cep,
            logradouro = logradouro,
            numero = numero,
            complemento = complemento,
            bairro = bairro,
            cidade = cidade,
            uf = uf
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (id == 0) {
                    // Verificar se o CNPJ ou CPF já existe para evitar duplicidade
                    val existente = repository.buscarPorCnpj(cnpj)
                    if (existente != null) {
                        erro.postValue("Este CPF/CNPJ já está cadastrado!")
                        return@withContext
                    }
                    repository.inserir(novoCliente)
                } else {
                    repository.atualizar(novoCliente)
                }
                atualizado.postValue(true)
            }
        }
    }
}