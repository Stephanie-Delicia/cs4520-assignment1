package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment1.R
import org.w3c.dom.Text

class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false);

        val navHostFragmentController =
            requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment)
                ?.findNavController()
        var loginBtn = view.findViewById<Button>(R.id.LoginButton)
        var userTextView = view.findViewById<EditText>(R.id.editTextUsername)
        var passTextView = view.findViewById<EditText>(R.id.editTextPassword)

        loginBtn.setOnClickListener{
            if (userTextView.text.toString().equals("admin") and passTextView.text.toString().equals("admin")) {
                Log.i("Location:", navHostFragmentController?.currentDestination.toString())
                Toast.makeText(requireActivity(), "YEEHAW.", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                //view.findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                //var fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                //fragmentTransaction.replace(R.id.loginFragment, ProductListFragment())
                //fragmentTransaction.commit()
                if (navHostFragmentController != null) {
                        navHostFragmentController.navigate(R.id.action_loginFragment_to_productListFragment)
                    }
            } else {
                Toast.makeText(requireActivity(), "Please enter valid username and password. Hint: admin", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}