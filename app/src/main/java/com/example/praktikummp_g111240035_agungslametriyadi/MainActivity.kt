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
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DBHelper

    fun getMD5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bytes = md.digest(input.toByteArray())
        // Konversi ke Hexa
        val hexString = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }

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

        databaseHelper = DBHelper(this)

        btnMasuk.setOnClickListener {
            val dataNIM:String = inputNIM.text.toString()
            val dataPassword:String = inputPassword.text.toString()

            // Ubah Password ke MD5
            val hashedPassword = getMD5Hash(dataPassword)
            // Buat Kueri
            val query = "SELECT * FROM TBL_MHS WHERE nim = '"+dataNIM+"' " +
                    "AND password = '"+hashedPassword+"'"
            // Buka Akses DB
            val db = databaseHelper.readableDatabase
            val cursor = db.rawQuery(query, null)

            // Cek Hasil Kueri
            val result = cursor.moveToFirst()
            if(result == true)
            {
                // Login Benar
                val dataNama = cursor.getString(cursor
                    .getColumnIndexOrThrow("nama"))
                val dataEmail = cursor.getString(cursor
                    .getColumnIndexOrThrow("email"))
                // Kirimkan data via Intent
                var intent:Intent = Intent(this,ProfileMahasiswa::class.java)
                intent.putExtra("nim",dataNIM)
                intent.putExtra("nama",dataNama)
                intent.putExtra("email",dataEmail)
                startActivity(intent)
            }
            else {
                // Login Salah-> Bersihkan Semua
                Toast.makeText(applicationContext,"Data Login Salah",
                    Toast.LENGTH_SHORT).show()
                inputNIM.setText("")
                inputPassword.setText("")
            }

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