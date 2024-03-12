package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment1.ApiService
import com.cs4520.assignment1.R
import com.cs4520.assignment1.RetrofitClient
import com.cs4520.assignment1.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var loginBtn : Button
    private lateinit var userTextView : EditText
    private lateinit var passTextView : EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var navHostFragmentController : NavController

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        // val view = inflater.inflate(R.layout.fragment_login, container, false)

        navHostFragmentController =
            requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment)
                ?.findNavController()!!
        loginBtn = binding.LoginButton
        userTextView = binding.editTextUsername
        passTextView = binding.editTextPassword
        progressBar = binding.progressBar

        loginBtn.setOnClickListener{
            if ((userTextView.text.toString() == "admin") and (passTextView.text.toString() == "admin")) {
               Toast.makeText(requireActivity(), "Successful login.", Toast.LENGTH_SHORT).show()

                progressBar.visibility = View.VISIBLE
                retrieveProductData(progressBar)
                //navHostFragmentController.navigate(R.id.action_loginFragment_to_productListFragment)
                //userTextView.setText("")
                //passTextView.setText("")
            } else {
                Toast.makeText(requireActivity(), "Invalid username/password. Hint: admin", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    fun retrieveProductData(progressBar: ProgressBar) {
        val service = RetrofitClient.retrofit.create(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
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
            progressBar.visibility = View.INVISIBLE
        }
    }
}