package com.caiquesenna.cadastrocliente.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.caiquesenna.cadastrocliente.databinding.ActivityCadastroClienteBinding
import com.caiquesenna.cadastrocliente.model.Cliente
import com.caiquesenna.cadastrocliente.viewmodel.CadastroViewModel

class CadastroClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroClienteBinding
    private val viewModel: CadastroViewModel by viewModels()
    private var cliente: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera o cliente se for edição
        cliente = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("cliente", Cliente::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("cliente") as? Cliente
        }

        // Configuração inicial de tipo
        if (cliente == null) {
            binding.btnPJ.isSelected = true
        }

        //Preenche os campos com os dados atuais do cliente
        cliente?.let {
            preencherCampos(it)
        }

        //Observa erros de validacao
        viewModel.erro.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        //Observa dados do CNPJ buscado
        viewModel.dadosCnpj.observe(this) { empresa ->
            empresa?.let {
                binding.etNomeRazaoSocial.setText(it.nome)
                binding.etNomeFantasia.setText(it.fantasia)
                binding.etEmail.setText(it.email)
                binding.etTelefone.setText(it.telefone)
                binding.etCep.setText(it.cep)
                binding.etLogradouro.setText(it.logradouro)
                binding.etNumero.setText(it.numero)
                binding.etComplemento.setText(it.complemento)
                binding.etBairro.setText(it.bairro)
                binding.etCidade.setText(it.municipio)
                binding.etUf.setText(it.uf)
            }
        }

        //Fecha a tela quando o cliente for atualizado/salvo
        viewModel.atualizado.observe(this) { ok ->
            if (ok) {
                val acao = if (cliente == null) "cadastrado" else "atualizado"
                Toast.makeText(this, "Cliente $acao!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.btnBuscarCnpj.setOnClickListener {
            val cnpj = binding.etCpfCnpj.text.toString()
            if (cnpj.isNotEmpty()) {
                viewModel.buscarCnpj(cnpj)
            } else {
                Toast.makeText(this, "Digite um CNPJ", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSalvar.setOnClickListener {
            viewModel.salvar(
                id = cliente?.id ?: 0,
                tipo = if (binding.btnPJ.isSelected) "PJ" else "PF",
                razao = binding.etNomeRazaoSocial.text.toString(),
                fantasia = binding.etNomeFantasia.text.toString(),
                cnpj = binding.etCpfCnpj.text.toString(),
                inscricao = binding.etInscricaoSocial.text.toString(),
                email = binding.etEmail.text.toString(),
                telefone = binding.etTelefone.text.toString(),
                cep = binding.etCep.text.toString(),
                logradouro = binding.etLogradouro.text.toString(),
                numero = binding.etNumero.text.toString(),
                complemento = binding.etComplemento.text.toString(),
                bairro = binding.etBairro.text.toString(),
                cidade = binding.etCidade.text.toString(),
                uf = binding.etUf.text.toString()
            )
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        // Lógica para alternar entre PF e PJ
        binding.btnPJ.setOnClickListener {
            binding.btnPJ.isSelected = true
            binding.btnPF.isSelected = false
            // Você pode mudar o background ou cor aqui se quiser visualmente
        }

        binding.btnPF.setOnClickListener {
            binding.btnPF.isSelected = true
            binding.btnPJ.isSelected = false
        }
    }

    private fun preencherCampos(it: Cliente) {
        binding.etNomeRazaoSocial.setText(it.razao)
        binding.etNomeFantasia.setText(it.fantasia)
        binding.etCpfCnpj.setText(it.cnpj)
        binding.etInscricaoSocial.setText(it.inscricao)
        binding.etEmail.setText(it.email)
        binding.etTelefone.setText(it.telefone)
        binding.etCep.setText(it.cep)
        binding.etLogradouro.setText(it.logradouro)
        binding.etNumero.setText(it.numero)
        binding.etComplemento.setText(it.complemento)
        binding.etBairro.setText(it.bairro)
        binding.etCidade.setText(it.cidade)
        binding.etUf.setText(it.uf)
    }
}