package com.cs4520.assignment1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.cs4520.assignment1.ui.theme.Assignment1Theme

class MainActivity : FragmentActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout)

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.navFragment)
        //       as NavHostFragment

        //navController = navHostFragment.navController
    }

    //override fun onNavigateUp(): Boolean {
     //   navController = findNavController(R.id.navFragment)
      //  return navController.navigateUp() || super.onNavigateUp()
  //  }
}