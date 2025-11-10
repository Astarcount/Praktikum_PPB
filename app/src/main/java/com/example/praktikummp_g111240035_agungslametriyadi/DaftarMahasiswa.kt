package com.example.praktikummp_g111240035_agungslametriyadi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Patterns
import java.security.MessageDigest

class DaftarMahasiswa : AppCompatActivity() {

    fun isValidEmail(email:String):Boolean {
        val result:Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return result
    }

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
        setContentView(R.layout.activity_daftar_mahasiswa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        // Kode Inisialisasi
        var inputDataNIM:EditText = findViewById<EditText>(R.id.inputDaftarNIM)
        var inputDataNama:EditText = findViewById<EditText>(R.id.inputDaftarNama)
        var inputDataEmail:EditText = findViewById<EditText>(R.id.inputDaftarEmail)
        var inputDataPassword:EditText = findViewById<EditText>(R.id.inputDaftarPassword)
        val btnAksiDaftar:Button = findViewById<Button>(R.id.btnAksiDaftar)
        val btnAksiBersih:Button = findViewById<Button>(R.id.btnAksiBersih)
        val btnAksiBatal:Button = findViewById<Button>(R.id.btnAksiBatal)

        btnAksiDaftar.setOnClickListener {

            // Ambil Data dari Edit Text
            val NIM:String = inputDataNIM.text.toString()
            val Nama:String = inputDataNama.text.toString()
            val Email:String = inputDataEmail.text.toString()
            val Password:String = inputDataPassword.text.toString()

            if(NIM.equals("")||Nama.equals("")||Email.equals("")||Password.equals(""))
            {
                Toast.makeText(this,"Input Data Masih Kosong",Toast.LENGTH_SHORT).show()
            }
            else
            {

                if(!isValidEmail(Email))
                {
                    Toast.makeText(this,"E-Mail Tidak Valid",Toast.LENGTH_SHORT).show()
                }
                else
                {

                    // Dapatkan Hash Password
                    val HashedPassword = getMD5Hash(Password)
                    Toast.makeText(this,"NIM : "+NIM+
                            " Nama : "+Nama,Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"Email : "+Email+
                            " Password : "+HashedPassword,Toast.LENGTH_SHORT).show()

                }

            }

        }
        btnAksiBersih.setOnClickListener {

            inputDataNIM.setText("")
            inputDataNama.setText("")
            inputDataEmail.setText("")
            inputDataPassword.setText("")

        }

        btnAksiBatal.setOnClickListener {
            //batalkan dengan finish
            finish()
        }
    }
}