package com.example.firebasestorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun descargar(view: View){

        val storage = Firebase.storage
        //Crear referencia
        var storageRef = storage.reference
        var imagesRef: StorageReference? = storageRef.child("imagen")
        var estrellaRef = storageRef.child("imagen/estrellafugaz.png")
        var imgV:ImageView = findViewById(R.id.imgFoto)

        val localFile = File.createTempFile("imagen", "png")

        estrellaRef.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    fun cargar(view: View){

        val storage = Firebase.storage
        //Crear referencia
        var storageRef = storage.reference
        var imagesRef: StorageReference? = storageRef.child("imagen")

        var estrellaRef = storageRef.child("imagen/estrellafugaz.png")

        var imgV:ImageView = findViewById(R.id.imgFoto)

        val ONE_MEGABYTE: Long = 1024 * 1024
        estrellaRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            Glide.with(this /* context */)
                .load(it)
                .into(imgV)
        }.addOnFailureListener {

        }
    }

}