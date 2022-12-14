package com.cpadridev.carmonaadrian_enterthecaradmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecaradmin.databinding.ActivityInsertBinding
import com.cpadridev.carmonaadrian_enterthecaradmin.model.Vehicle

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
            if (type.text.isNotEmpty() && price.text.isNotEmpty()) {
                val bundle = Bundle()

                bundle.putParcelable("Vehicle", Vehicle(null, type.text.toString(), price.text.toString().toInt()))

                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, bundle)
                }

                setResult(RESULT_OK, intent)

                finish()
            } else {
                Toast.makeText(applicationContext, getString(R.string.error_fill_fields), Toast.LENGTH_LONG).show()
            }
        }
    }
}