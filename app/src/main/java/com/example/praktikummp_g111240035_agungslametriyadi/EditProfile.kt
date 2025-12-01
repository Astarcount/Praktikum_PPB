package com.example.praktikummp_g111240035_agungslametriyadi

import android.content.ContentValues
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.MessageDigest

class EditProfile : AppCompatActivity() {

    private lateinit var databaseHelper: DBHelper

    fun getMD5Hash(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bytes = md.digest(input.toByteArray())
        // convert the byte array to hexadecimal string
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
    fun isValidEmail(email: String): Boolean {
        val result:Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Kode Inisialisasi
        var editNIM = findViewById<EditText>(R.id.editNIM)
        var editNama = findViewById<EditText>(R.id.editNama)
        var editEmail = findViewById<EditText>(R.id.editEmail)
        var editPassword = findViewById<EditText>(R.id.editPassword)

        // Ambil dan Tampilkan Data Intent
        val dataNIM:String = intent.getStringExtra("nim").toString()
        val dataNama:String = intent.getStringExtra("nama").toString()
        val dataEmail:String = intent.getStringExtra("email").toString()
        editNIM.setText(dataNIM)
        editNama.setText(dataNama)
        editEmail.setText(dataEmail)

        // Kunci Akses editNIM
        editNIM.isFocusable = false
        editNIM.isFocusableInTouchMode = false

        // Inisialisasi Button dan Listener
        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasi)
        val btnBatal = findViewById<Button>(R.id.btnBatal)

        databaseHelper = DBHelper(this)

        btnKonfirmasi.setOnClickListener {

            val newNama:String = editNama.text.toString()
            val newEmail:String = editEmail.text.toString()
            val dataPassword:String = editPassword.text.toString()
            // Verifikasi Data
            if(newNama.equals("")||newEmail.equals("")||dataPassword.equals(""))
            {
                Toast.makeText(applicationContext,"Kolom Kosong",
                    Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(isValidEmail(newEmail))
                {
                    val hashedPassword:String = getMD5Hash(dataPassword)
                    // Buka Akses Tulis DB
                    val db = databaseHelper.writableDatabase
                    val values = ContentValues().apply {
                        put("nama", newNama)
                        put("email", newEmail)
                    }
                    val result = db.update("TBL_MHS", values,
                        "nim = ? AND password = ?", arrayOf(dataNIM,hashedPassword))
                    db.close()

                    // Cek Sukses Kueri
                    if(result > 0)
                    {
                        Toast.makeText(applicationContext,"Update Berhasil",
                            Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else
                        Toast.makeText(applicationContext,"Update Gagal",
                            Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(applicationContext,"E-Mail Tidak Valid",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }
        btnBatal.setOnClickListener {
            finish()
        }
    }
}