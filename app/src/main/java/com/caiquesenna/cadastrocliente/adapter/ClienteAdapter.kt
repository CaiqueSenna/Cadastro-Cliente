package com.caiquesenna.cadastrocliente.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caiquesenna.cadastrocliente.R
import com.caiquesenna.cadastrocliente.model.Cliente

class ClienteAdapter(
    private val onEditar: (Cliente) -> Unit,
    private val onExcluir: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ViewHolder>() {

    private var lista: List<Cliente> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun atualizarLista(novaLista: List<Cliente>) {
        this.lista = novaLista
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNome)
        val tvFantasia: TextView = view.findViewById(R.id.tvFantasia)
        val tvCpfCnpj: TextView = view.findViewById(R.id.tvCpfCnpj)
        val tvCidade: TextView = view.findViewById(R.id.tvCidade)
        val tvCodigo: TextView = view.findViewById(R.id.tvCodigo)
        val btnEditar: View = view.findViewById(R.id.btnEditar)
        val btnExcluir: View = view.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = lista[position]
        holder.tvNome.text = cliente.razao
        holder.tvFantasia.text = cliente.fantasia
        holder.tvCpfCnpj.text = cliente.cnpj
        holder.tvCodigo.text = "Codigo: ${cliente.id}"
        holder.tvCidade.text = "${cliente.cidade} - ${cliente.uf}"

        holder.btnEditar.setOnClickListener { onEditar(cliente) }
        holder.btnExcluir.setOnClickListener { onExcluir(cliente) }
    }
}