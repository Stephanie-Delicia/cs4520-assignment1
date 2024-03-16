package com.cs4520.assignment1.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresExtension
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.cs4520.assignment1.ProductDB
import com.cs4520.assignment1.R
import com.cs4520.assignment1.RecyclerAdapter
import com.cs4520.assignment1.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerAdapter? = null
    private var database: ProductDB? = null
    private var initialLoading : Boolean = false
    private var refreshButton: Button? = null
    private var noProductsText : TextView? = null
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    fun getDatabase() : ProductDB? {
        return database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Database retrieval
        database = activity?.let {
            Room.databaseBuilder(
                it,
                ProductDB::class.java,
                "product_table"
            ).fallbackToDestructiveMigration().build()
        }

        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        val view = binding.root
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = RecyclerAdapter(binding.progressBar,
            binding,
            this)
        refreshButton = binding.refreshButton
        noProductsText = binding.noProductsTextView

        if (!initialLoading) {
            adapter!!.retrieveProductData()
            initialLoading = true
        }

        refreshButton!!.setOnClickListener {
            adapter!!.retrieveProductData()
            noProductsText!!.visibility = View.INVISIBLE
            Log.i("refreshButton data:", adapter!!.productList.toString())
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }
}
