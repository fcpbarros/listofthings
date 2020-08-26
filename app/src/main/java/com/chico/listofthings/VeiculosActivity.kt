package com.chico.listofthings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chico.listofthings.dialogs.AdicionaVeiculoDialog
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veiculos)

        novo_veiculo.setOnClickListener {
            AdicionaVeiculoDialog(viewDaActivity as ViewGroup, this)
                .adicionaVeiculo { novoVeiculo ->
                    if (veiculos.contains(novoVeiculo)) {
                        Toast.makeText(this, "Esse veículo já existe", Toast.LENGTH_LONG).show()
                    } else {
                        db.collection(COLECAO).document(novoVeiculo.id!!)
                            .set(novoVeiculo)
                            .addOnSuccessListener {
                                Log.d(
                                    "Teste",
                                    "Documento adicionado no Firestore com o ID " + novoVeiculo.toString()
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w("Teste", "Error adding document", e)
                            }
                    }

                }

        }
    }

}