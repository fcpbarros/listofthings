package com.chico.listofthings.dialogs

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.chico.listofthings.R
import com.chico.listofthings.modelo.Veiculo
import kotlinx.android.synthetic.main.form_veiculo.view.*

class AlteraVeiculoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCriada = criaLayout()
    private val campoPlaca = viewCriada.editText_placa_dialog
    private val campoId = viewCriada.editText_id_dialog
    private val campoCor = viewCriada.editText_cor_dialog
    private val campoModelo = viewCriada.editText_veiculo_dialog

    fun mudaVeiculo(
        veiculo: Veiculo,
        delegate: (veiculo: Veiculo) -> Unit
    ) {

        configuraCampos(veiculo)
        configuraFormulario(delegate)

    }

    private fun configuraCampos(veiculo: Veiculo) {
        campoCor.setText(veiculo.cor)
        campoId.setText(veiculo.id)
        campoModelo.setText(veiculo.modelo)
        campoPlaca.setText(veiculo.placa)
    }

    private fun configuraFormulario(delegate: (veiculo: Veiculo) -> Unit) {

        val titulo = "Alterar veículo"

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Alterar") { dialogInterface: DialogInterface, i: Int ->
                val novaId = campoId.text.toString()
                val novaCor = campoCor.text.toString()
                val novaPlaca = campoPlaca.text.toString()
                val novoModelo = campoModelo.text.toString()

                val veiculoCriado = Veiculo(
                    id = novaId,
                    cor = novaCor,
                    modelo = novoModelo,
                    placa = novaPlaca
                )
                delegate(veiculoCriado)
            }
            .setNegativeButton("Cancelar", null)
            .show()

    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.form_veiculo,
            viewGroup,
            false
        )
    }

}