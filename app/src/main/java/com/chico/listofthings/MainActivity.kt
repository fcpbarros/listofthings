package com.chico.listofthings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val mStorageRef: StorageReference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        novo_veiculo.setOnClickListener {
            Toast.makeText(this, "Veículo adicionado", Toast.LENGTH_LONG).show()
        }
    }
}