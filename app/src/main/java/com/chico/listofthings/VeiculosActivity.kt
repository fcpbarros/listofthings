package com.chico.listofthings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chico.listofthings.adapter.VeiculosAdapter
import com.chico.listofthings.dialogs.AdicionaVeiculoDialog
import com.chico.listofthings.dialogs.AlteraVeiculoDialog
import com.chico.listofthings.modelo.Veiculo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_veiculos.*


class VeiculosActivity : AppCompatActivity() {


    private val COLECAO = "Veículos"
    private var listaId: MutableList<String> = mutableListOf()
    private var veiculos: MutableList<Veiculo> = mutableListOf()
    private val db = FirebaseFirestore.getInstance()
    private val mStorageRef: StorageReference = FirebaseStorage.getInstance().reference
    private val viewDaActivity: View by lazy {
        window.decorView
    }
    private lateinit var adapterVeiculos: VeiculosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veiculos)

        pegaIDs()

        novo_veiculo.setOnClickListener {
            AdicionaVeiculoDialog(viewDaActivity as ViewGroup, this)
                .adicionaVeiculo { novoVeiculo ->
                    if (listaId.contains(novoVeiculo.id)) {
                        Toast.makeText(
                            this, "Esse veículo já existe",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        adicionaVeiculoFirestore(novoVeiculo)
                        atualizaLista()
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        atualizaLista()
    }

    private fun atualizaLista() {
        veiculos.clear()
        configuraLista()
    }

    fun configuraLista() {
        //preparar o adapter
        adapterVeiculos = VeiculosAdapter(veiculos, this)
        pegaVeiculos()

        with(lista_veiculos) {
            adapter = adapterVeiculos
            setOnItemClickListener { _, _, posicao, _ ->
                val veiculoClicado = veiculos[posicao]
                AlteraVeiculoDialog(viewDaActivity as ViewGroup, this@VeiculosActivity)
                    .mudaVeiculo(veiculoClicado) { veiculoAlterado ->
                        adicionaVeiculoFirestore(veiculoAlterado)
                        atualizaLista()
                    }
            }
        }
    }

    private fun pegaVeiculos() {
        db.collection(COLECAO)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Teste", "${document.id} => ${document.data}")
                    val amostra = document.toObject(Veiculo::class.java)
                    veiculos.add(amostra)
                    adapterVeiculos.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Teste", "Error getting documents: ", exception)
            }
    }


    private fun adicionaVeiculoFirestore(veiculo: Veiculo) {
        db.collection(COLECAO).document(veiculo.id!!)
            .set(veiculo)
            .addOnSuccessListener {
                Toast.makeText(
                    this, "Veiculo adicionado:" + veiculo.id,
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this, "Erro ao adicionar o veículo. " +
                            "Por favor, checar a conexão com a internet",
                        Toast.LENGTH_LONG
                    ).show()
                }

    }


    private fun pegaIDs() {
        db.collection(COLECAO)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val amostra = document.toObject(Veiculo::class.java)
                    Log.d("testeID", "${amostra.id}")
                    listaId.add(amostra.id!!)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Teste", "Error getting documents: ", exception)
            }
    }

}