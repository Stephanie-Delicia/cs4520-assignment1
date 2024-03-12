package com.cs4520.assignment1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.cs4520.assignment1.RetrofitClient.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        val button = findViewById<Button>(R.id.api_bttn_test)

        val service = retrofit.create(ApiService::class.java)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val response = service.getAllData("1")
                if (response.isSuccessful) {
                    // need to filter the list of data
                    // if empty, give toast error
                    // page 5 is empty!
                    Log.i("API Call:", response.toString())
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                            Log.i("Actual Data:", data.toString())
                            Log.i("Sample Data:", data[0].getName().toString())
                    } else {
                        Log.i("API Call:", "Empty data.")
                    }
                } else {
                    Log.i("API Call:", "Failed to fetch data.")
                }

            }
        }
    }
}