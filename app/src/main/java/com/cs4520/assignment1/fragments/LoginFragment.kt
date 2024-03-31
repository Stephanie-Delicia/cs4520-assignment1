//package com.cs4520.assignment1.fragments
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
//import com.cs4520.assignment1.R
//import com.cs4520.assignment1.databinding.FragmentLoginBinding
//
//class LoginFragment : Fragment() {
//
//    private lateinit var loginBtn : Button
//    private lateinit var userTextView : EditText
//    private lateinit var passTextView : EditText
//    private lateinit var navHostFragmentController : NavController
//
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        val view = binding.root
//        // Inflate the layout for this fragment
//        // val view = inflater.inflate(R.layout.fragment_login, container, false)
//
//        navHostFragmentController =
//            requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment)
//                ?.findNavController()!!
//        loginBtn = binding.LoginButton
//        userTextView = binding.editTextUsername
//        passTextView = binding.editTextPassword
//
//        loginBtn.setOnClickListener{
//            if ((userTextView.text.toString() == "admin") and (passTextView.text.toString() == "admin")) {
//               Toast.makeText(requireActivity(), "Successful login.", Toast.LENGTH_SHORT).show()
//                Log.i("Successful login", "LoginFragment")
//                navHostFragmentController.navigate(R.id.action_loginFragment_to_productListFragment)
//                userTextView.setText("")
//                passTextView.setText("")
//            } else {
//                Toast.makeText(requireActivity(), "Invalid username/password. Hint: admin", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        return view
//    }
//}