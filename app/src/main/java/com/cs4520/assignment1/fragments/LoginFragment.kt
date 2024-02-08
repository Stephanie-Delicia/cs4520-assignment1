package com.cs4520.assignment1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.cs4520.assignment1.R
import org.w3c.dom.Text

class LoginFragment : Fragment() {
    var userText : String = "";
    var passText : String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //if (savedInstanceState != null) {
        //    userText = savedInstanceState.getString("userText").toString()
        //    passText = savedInstanceState.getString("passText").toString()
        //}

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false);
        //val loginButton = view.findViewById<Button>(R.id.LoginButton);
        //if (savedInstanceState != null) {
         //   userText = savedInstanceState.getString("userText").toString()
         //   passText = savedInstanceState.getString("passText").toString()
       // }

        //val userTextView = view.findViewById<TextView>(R.id.editTextUsername)
        //val passTextView = view.findViewById<TextView>(R.id.editTextPassword)

        //userTextView.setText(userText)
        //passTextView.setText(passText)

        /*if (userText == "admin" && passText == "admin") {
            loginButton.setOnClickListener {
                Toast.makeText(requireActivity(), "YEEHAW", Toast.LENGTH_SHORT).show()
            }
        } else {
            loginButton.setOnClickListener {
                Toast.makeText(requireActivity(), userText, Toast.LENGTH_SHORT).show()
            }
        }*/
        var loginBtn = view.findViewById<Button>(R.id.LoginButton)
        var userTextView = view.findViewById<EditText>(R.id.editTextUsername)
        var passTextView = view.findViewById<EditText>(R.id.editTextPassword)
        // userTextView.setText("lol")

        loginBtn.setOnClickListener(View.OnClickListener {
            if (userTextView.text.toString().equals("admin") and passTextView.text.toString().equals("admin")) {
                Toast.makeText(requireActivity(), "YEEHAW", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "Please enter valid username and password. Hint: admin", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}