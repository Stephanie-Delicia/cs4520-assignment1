package com.cs4520.assignment1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.R
import com.cs4520.assignment1.RecyclerAdapter
import com.cs4520.assignment1.databinding.FragmentProductListBinding
import com.cs4520.assignment1.productsDataset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class ProductListFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerAdapter? = null
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
        adapter = RecyclerAdapter(binding.progressBar)

        if (adapter!!.productList.isNullOrEmpty()) {
            adapter!!.retrieveProductData()
            adapter!!.notifyDataSetChanged()
            if (adapter!= null) {
                Log.i("Testing if adapter was changed:", adapter!!.productList.toString())
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return view
    }
}
