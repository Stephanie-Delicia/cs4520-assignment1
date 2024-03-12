package com.cs4520.assignment1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            // setContent { IndeterminateCircularIndicator()}
            CoroutineScope(Dispatchers.IO).launch {
                // setContent {IndeterminateCircularIndicator()}
                // setContent { IndeterminateCircularIndicator()  }
                val response = service.getAllData("2")
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

@Composable
fun IndeterminateCircularIndicator() {
    var loading by remember {
        mutableStateOf(true) // initialize as true to first display progress
    }

    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    } else {return}

    loading = !loading
}