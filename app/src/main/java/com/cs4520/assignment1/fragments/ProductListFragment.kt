package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.RecyclerAdapter
import com.cs4520.assignment1.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerAdapter? = null
    private var initialLoading : Boolean = false
    private var refreshButton: Button? = null
    private var noProductsText : TextView? = null
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        val view = binding.root
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = RecyclerAdapter(binding.progressBar, binding)
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
