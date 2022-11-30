package com.cpadridev.carmonaadrian_enterthecaradmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecaradmin.databinding.ActivityInsertBinding

class Insert : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnInsert = binding.btnInsert
        val type = binding.edtType
        val price = binding.edtPrice

        btnInsert.setOnClickListener {
            val bundle = Bundle()

            bundle.putString("type", type.text.toString())
            bundle.putInt("price", price.text.toString().toInt())

            val intent = Intent().apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            setResult(RESULT_OK, intent)

            finish()
        }
    }
}