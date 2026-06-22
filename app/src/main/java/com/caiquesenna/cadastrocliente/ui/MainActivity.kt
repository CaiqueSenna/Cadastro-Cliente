package com.caiquesenna.cadastrocliente.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.caiquesenna.cadastrocliente.R
import com.caiquesenna.cadastrocliente.adapter.ClienteAdapter
import com.caiquesenna.cadastrocliente.databinding.ActivityMainBinding
import com.caiquesenna.cadastrocliente.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Cria o ViewModel ligado ao clico de vida desta Activity
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ClienteAdapter(
            onEditar = { cliente ->
                val intent = Intent(this, CadastroClienteActivity::class.java)
                intent.putExtra("cliente", cliente)
                startActivity(intent)
            },
            onExcluir = { cliente ->
                AlertDialog.Builder(this)
                    .setTitle("Excluir")
                    .setMessage("Excluir ${cliente.razao}?")
                    .setPositiveButton("Sim") { _, _ -> viewModel.deletar(cliente)}
                    .setNegativeButton("Não", null).show()
            }
        )

        binding.rvClientes.adapter = adapter
        binding.rvClientes.layoutManager = LinearLayoutManager(this)

        //Observa a lista: quando mudar, atualizar a RecyclerView
        viewModel.clientes.observe(this) {lista ->
            adapter.atualizarLista(lista)
            
            // Atualiza o total de clientes
            binding.tvTotal.text = "${lista.size} clientes cadastrados"
            
            // Mostra mensagem se a lista estiver vazia
            if (lista.isEmpty()) {
                binding.tvVazio.visibility = android.view.View.VISIBLE
                binding.rvClientes.visibility = android.view.View.GONE
            } else {
                binding.tvVazio.visibility = android.view.View.GONE
                binding.rvClientes.visibility = android.view.View.VISIBLE
            }
        }

        //Observa mensagem de feedback
        viewModel.mensagem.observe(this) {msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        binding.butNovo.setOnClickListener {
            val intent = Intent(this, CadastroClienteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregarClientes()
    }
}