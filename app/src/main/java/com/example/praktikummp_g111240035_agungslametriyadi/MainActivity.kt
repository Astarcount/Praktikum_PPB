package com.example.praktikummp_g111240035_agungslametriyadi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Inisialisasi Objek Activity
        val inputNIM = findViewById<EditText>(R.id.inputNIM)
        var inputPassword = findViewById<EditText>(R.id.inputPassword)
        val btnMasuk = findViewById<Button>(R.id.btnMasuk)
        val btnDaftar = findViewById<Button>(R.id.btnDaftar)
        val btnTutup = findViewById<Button>(R.id.btnTutup)

        btnMasuk.setOnClickListener {
            val dataNIM:String = inputNIM.text.toString()
            val dataPassword:String = inputPassword.text.toString()
            // Tampilkan Hasil
            Toast.makeText(applicationContext,dataNIM+" "+dataPassword,Toast.LENGTH_SHORT)
                .show()
        }
        btnDaftar.setOnClickListener {
            // Buat variabel intent
            val intent:Intent = Intent(this, DaftarMahasiswa::class.java)
            // Jalankan Intent
            startActivity(intent)
        }
        btnTutup.setOnClickListener {
            // Perintahkan Activity untuk Selesai
            finish()
        }

    }
}