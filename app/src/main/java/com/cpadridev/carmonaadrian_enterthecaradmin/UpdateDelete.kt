package com.cpadridev.carmonaadrian_enterthecaradmin

import android.content.Intent
import android.os.Bundle
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
            val bundle = Bundle()

            bundle.putParcelable("Vehicle", Vehicle(id.text.toString().toInt(), type.text.toString(), price.text.toString().toInt()))

            val intent = Intent().apply {
                putExtra("Delete", false)
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            setResult(RESULT_OK, intent)

            finish()
        }

        btnDelete.setOnClickListener {
            val intent = Intent().apply {
                putExtra("Delete", true)
                putExtra("Id", id.text.toString().toInt())
            }

            setResult(RESULT_OK, intent)

            finish()
        }
    }
}