package com.cpadridev.carmonaadrian_enterthecaradmin

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpadridev.carmonaadrian_enterthecaradmin.connection.ApiEnterTheCarVehcicles
import com.cpadridev.carmonaadrian_enterthecaradmin.connection.Client
import com.cpadridev.carmonaadrian_enterthecaradmin.databinding.ActivityMainBinding
import com.cpadridev.carmonaadrian_enterthecaradmin.model.Vehicle
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var retrofit: Retrofit?= null
    private var vehicleAdapter: VehicleAdapter ?=null
    private var pressedPosition: Int = -1

    private val insertResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bundle = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            val vehicle = bundle?.getParcelable<Vehicle>("Vehicle")

            if (vehicle != null) {
                insertData(vehicle)
            }
        }
    }

    private val updateDeleteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bundle = result.data?.getBundleExtra(Intent.EXTRA_TEXT)

            if(bundle?.getBoolean("Delete", true) == true){
                showDeleteDialog(bundle)
            }
            else{
                val vehicle: Vehicle? = bundle?.getParcelable("Vehicle")

                if (vehicle != null) {
                    updateData(vehicle)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.ryvVehicles
        val fab = binding.fab

        recycler.setHasFixedSize(true)

        recycler.addItemDecoration(DividerItemDecoration(this, 1))

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        vehicleAdapter = VehicleAdapter()

        vehicleAdapter?.setOnItemClickListerner {
            pressedPosition = recycler.getChildAdapterPosition(it)

            val bundle = Bundle()

            bundle.putParcelable("Vehicle", vehicleAdapter?.getItem(pressedPosition))

            val intent = Intent(this, UpdateDelete::class.java).apply {
                putExtra(Intent.EXTRA_TEXT, bundle)
            }

            updateDeleteResult.launch(intent)
        }

        recycler.adapter = vehicleAdapter

        fab.setOnClickListener {
            val intent = Intent(this, Insert::class.java)

            insertResult.launch(intent)
        }

        retrofit = Client.getClient()

        getData()
    }

    private fun getData() {
        val api: ApiEnterTheCarVehcicles? = retrofit?.create(ApiEnterTheCarVehcicles::class.java)

        api?.getVehicles()?.enqueue(object : Callback<ArrayList<Vehicle>> {
            override fun onResponse(call: Call<ArrayList<Vehicle>>, response: Response<ArrayList<Vehicle>>) {
                if (response.isSuccessful) {
                    val vehiclesList = response.body()

                    if (vehiclesList != null) {
                        vehicleAdapter?.addToList(vehiclesList)
                    }
                } else
                    Toast.makeText(applicationContext, getString(R.string.error_response), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Vehicle>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun insertData(vehicle: Vehicle){
        val api: ApiEnterTheCarVehcicles? = retrofit?.create(ApiEnterTheCarVehcicles::class.java)

        api?.saveVehicles(vehicle.type, vehicle.price)?.enqueue(object :
            Callback<Vehicle> {
            override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                if (response.isSuccessful) {
                    val vehic = response.body()

                    if (vehic != null) {
                        vehicleAdapter?.addToList(vehic)
                        Snackbar.make(binding.root, getString(R.string.successful_insert_vehicle), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.accept)){
                            }
                            .show()
                    }
                } else
                    Toast.makeText(applicationContext, getString(R.string.error_response), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateData(vehicle: Vehicle){
        val api: ApiEnterTheCarVehcicles? = retrofit?.create(ApiEnterTheCarVehcicles::class.java)

        vehicle.id?.let {
            api?.updateVehicle(it, vehicle)?.enqueue(object : Callback<Vehicle> {
                override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                    if (response.isSuccessful) {
                        val vehic = response.body()

                        if (vehic != null) {
                            vehicleAdapter?.updateList(pressedPosition, vehic)
                            Snackbar.make(binding.root, getString(R.string.successful_update_vehicle), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.accept)){
                                }
                                .show()
                        }
                    } else
                        Toast.makeText(applicationContext, getString(R.string.error_response), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun deleteData(id: Int){
        val api: ApiEnterTheCarVehcicles? = retrofit?.create(ApiEnterTheCarVehcicles::class.java)

        api?.deleteVehicle(id)?.enqueue(object : Callback<Vehicle> {
            override fun onResponse(call: Call<Vehicle>, response: Response<Vehicle>) {
                if (response.isSuccessful) {
                    val vehic = response.body()

                    if (vehic != null) {
                        vehicleAdapter?.deleteFromList(pressedPosition)
                        Snackbar.make(binding.root, getString(R.string.successful_delete_vehicle), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.accept)){
                            }
                            .show()
                    }
                } else
                    Toast.makeText(applicationContext, getString(R.string.error_response), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Vehicle>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDeleteDialog(bundle: Bundle) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_delete_vehicle))
        builder.setMessage(getString(R.string.question_delete_vehicle))

        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            deleteData(bundle.getInt("id", 0))
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            Toast.makeText(applicationContext, getString(R.string.information_message_cancel_delete), Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }
}