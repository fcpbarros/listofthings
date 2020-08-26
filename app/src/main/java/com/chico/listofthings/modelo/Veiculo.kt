package com.chico.listofthings.modelo

class Veiculo(
    val id: String? = null,
    val modelo: String? = null,
    val placa: String? = null,
    val cor: String? = null
) {


    override fun toString(): String {
        return "id:,$id,\nmodelo:,$modelo,\nplaca:,$placa,\ncor:,$cor"
    }
}