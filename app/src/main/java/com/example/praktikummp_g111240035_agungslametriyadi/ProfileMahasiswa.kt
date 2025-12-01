package com.example.praktikummp_g111240035_agungslametriyadi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileMahasiswa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_mahasiswa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi Objek
        var txtNIM = findViewById<TextView>(R.id.textNIM)
        var txtNama = findViewById<TextView>(R.id.textNama)
        var txtEmail = findViewById<TextView>(R.id.textEmail)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnKeluar = findViewById<Button>(R.id.btnKeluar)

        // Ambil Data Intent
        val dataNIM:String = intent.getStringExtra("nim").toString()
        val dataNama:String = intent.getStringExtra("nama").toString()
        val dataEmail:String = intent.getStringExtra("email").toString()
        // Tampilkan Data Intent
        txtNIM.setText("NIM : " + dataNIM)
        txtNama.setText("Nama : " + dataNama)
        txtEmail.setText("E-Mail : " + dataEmail)

        // Button Ubah
        btnEdit.setOnClickListener{
            var intent: Intent = Intent(this,EditProfile::class.java)
            intent.putExtra("nim",dataNIM)
            intent.putExtra("nama",dataNama)
            intent.putExtra("email",dataEmail)
            startActivity(intent)
        }
        // Button Keluar
        btnKeluar.setOnClickListener {
            finish()
        }
    }
}