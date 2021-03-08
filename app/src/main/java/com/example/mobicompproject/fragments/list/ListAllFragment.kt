package com.example.mobicompproject.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobicompproject.R
import com.example.mobicompproject.database.ReminderViewModel
import com.example.mobicompproject.databinding.FragmentListAllBinding

class ListAllFragment : Fragment() {
    private var _binding: FragmentListAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListAllBinding.inflate(inflater, container, false)

        //UserViewModel
        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        //RecyclerView
        val adapter = ReminderItemAdapter(this.reminderViewModel)
        val recyclerView = binding.recyclerviewListall
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        reminderViewModel.readAll.observe(viewLifecycleOwner, { reminder ->
            adapter.setData(reminder)
        })

        binding.floatingActionButtonListall.setOnClickListener {
            findNavController().navigate(R.id.action_listAllFragment_to_addFragment)
        }



        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}