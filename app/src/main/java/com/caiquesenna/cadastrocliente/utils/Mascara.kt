package com.caiquesenna.cadastrocliente.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Mascara {
    fun validarCpf(cpf: String): Boolean {
        val nums = cpf.replace("[^0-9]".toRegex(), "")
        if (nums.length != 11) return false
        if (nums.all { it == nums[0] }) return false

        var soma = 0
        for (i in 0..8) soma += nums[i].digitToInt() * (10 - i)
        var resto = (soma * 10) % 11
        if (resto == 10 || resto == 11) resto = 0
        if (resto != nums[9].digitToInt()) return false

        soma = 0
        for (i in 0..9) soma += nums[i].digitToInt() * (11 - i)
        resto = (soma * 10) % 11
        if (resto == 10 || resto == 11) resto = 0
        return resto == nums[10].digitToInt()
    }

    fun validarCnpj(cnpj: String): Boolean {
        val nums = cnpj.replace("[^0-9]".toRegex(), "")
        if (nums.length != 14) return false
        if (nums.all { it == nums[0] }) return false

        val pesos1 = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
        val pesos2 = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

        var soma = 0
        for (i in 0..11) soma += nums[i].digitToInt() * pesos1[i]
        var resto = soma % 11
        val d1 = if (resto < 2) 0 else 11 - resto
        if (d1 != nums[12].digitToInt()) return false

        soma = 0
        for (i in 0..12) soma += nums[i].digitToInt() * pesos2[i]
        resto = soma % 11
        val d2 = if (resto < 2) 0 else 11 - resto
        return d2 == nums[13].digitToInt()
    }

    fun validarEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

object Formatters {

    fun formatarCpf(cpf: String): String {
        val n = cpf.replace("[^0-9]".toRegex(), "")
        return if (n.length == 11)
            "${n.substring(0, 3)}.${n.substring(3, 6)}.${n.substring(6, 9)}-${n.substring(9)}"
        else cpf
    }

    fun formatarCnpj(cnpj: String): String {
        val n = cnpj.replace("[^0-9]".toRegex(), "")
        return if (n.length == 14)
            "${n.substring(0, 2)}.${n.substring(2, 5)}.${n.substring(5, 8)}/${n.substring(8, 12)}-${n.substring(12)}"
        else cnpj
    }

    fun formatarCep(cep: String): String {
        val n = cep.replace("[^0-9]".toRegex(), "")
        return if (n.length == 8) "${n.substring(0, 5)}-${n.substring(5)}" else cep
    }

    fun formatarTelefone(tel: String): String {
        val n = tel.replace("[^0-9]".toRegex(), "")
        return when (n.length) {
            10 -> "(${n.substring(0, 2)}) ${n.substring(2, 6)}-${n.substring(6)}"
            11 -> "(${n.substring(0, 2)}) ${n.substring(2, 7)}-${n.substring(7)}"
            else -> tel
        }
    }

    fun formatarData(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        return sdf.format(Date(timestamp))
    }
}

// TextWatcher helper para máscara de CPF/CNPJ
fun EditText.aplicarMascaraCpfCnpj() {
    addTextChangedListener(object : TextWatcher {
        var isUpdating = false
        var old = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) return
            val str = s.toString().replace("[^0-9]".toRegex(), "")
            isUpdating = true

            val masked = when {
                str.length <= 11 -> { // CPF
                    buildString {
                        str.forEachIndexed { i, c ->
                            if (i == 3 || i == 6) append('.')
                            if (i == 9) append('-')
                            append(c)
                        }
                    }
                }
                else -> { // CNPJ
                    buildString {
                        str.take(14).forEachIndexed { i, c ->
                            if (i == 2 || i == 5) append('.')
                            if (i == 8) append('/')
                            if (i == 12) append('-')
                            append(c)
                        }
                    }
                }
            }

            if (masked != old) {
                setText(masked)
                setSelection(masked.length)
            }
            old = masked
            isUpdating = false
        }
    })
}

fun EditText.aplicarMascaraCep() {
    addTextChangedListener(object : TextWatcher {
        var isUpdating = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) return
            val str = s.toString().replace("[^0-9]".toRegex(), "").take(8)
            isUpdating = true
            val masked = if (str.length > 5) "${str.substring(0, 5)}-${str.substring(5)}" else str
            setText(masked)
            setSelection(masked.length)
            isUpdating = false
        }
    })
}