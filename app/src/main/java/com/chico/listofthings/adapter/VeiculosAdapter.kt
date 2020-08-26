package com.chico.listofthings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.chico.listofthings.R
import com.chico.listofthings.modelo.Veiculo
import kotlinx.android.synthetic.main.veiculo_item.view.*

class VeiculosAdapter(
    veiculos: List<Veiculo>,
    context: Context
) : BaseAdapter() {

    private val veiculos = veiculos
    private val context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //pegar a view
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.veiculo_item, parent, false)
        //pegar o item pra adaptar
        val veiculo = veiculos[position]
        //adiciona ID
        with(viewCriada) {
            veiculo_Id.text = veiculo.id
//            veiculo_cor.text = veiculo.cor
            veiculo_modelo.text = veiculo.modelo
//            veiculo_placa.text = veiculo.placa
        }
        return viewCriada

    }

    override fun getItem(position: Int): Veiculo {
        return veiculos[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return veiculos.size
    }


}