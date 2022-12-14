package com.cpadridev.carmonaadrian_enterthecaradmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpadridev.carmonaadrian_enterthecaradmin.databinding.ActivityUpdateDeleteBinding
import com.cpadridev.carmonaadrian_enterthecaradmin.model.Vehicle

class UpdateDelete : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDeleteBinding
    private lateinit var vehicle: Vehicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = binding.edtId
        val type = binding.edtType
        val price = binding.edtPrice
        val btnUpdate = binding.btnUpdate
        val btnDelete = binding.btnDelete

        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            val bundle = intent.getBundleExtra(Intent.EXTRA_TEXT)

            vehicle = bundle?.getParcelable("Vehicle")!!

            id.setText(vehicle.id.toString())
            type.setText(vehicle.type)
            price.setText(vehicle.price.toString())
        }

        btnUpdate.setOnClickListener {
            if (type.text.isNotEmpty() && price.text.isNotEmpty()) {
                val bundle = Bundle()

                bundle.putParcelable("Vehicle", Vehicle(id.text.toString().toInt(), type.text.toString(), price.text.toString().toInt()))
                bundle.putBoolean("Delete", false)

                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, bundle)
                }

                setResult(RESULT_OK, intent)

                finish()
            } else {
                Toast.makeText(applicationContext, getString(R.string.error_fill_fields), Toast.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {
            val bundle = Bundle()

            bundle.putBoolean("Delete", true)
            bundle.putInt("id", id.text.toString().toInt())

            val intent = Intent().apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            setResult(RESULT_OK, intent)

            finish()
        }
    }
}