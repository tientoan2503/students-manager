package com.example.studentsmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.studentsmanager.StudentAdapter
import com.example.studentsmanager.databinding.StudentsFragmentBinding
import com.example.studentsmanager.viewmodel.StudentViewModel
import com.example.studentsmanager.viewmodel.StudentViewModelFactory

class StudentsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = StudentsFragmentBinding.inflate(inflater, container, false)
        val viewModelFactory = StudentViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StudentViewModel::class.java)
        val adapter = StudentAdapter()

        binding.recyclerviewStudents.adapter = adapter
        viewModel.arrayStudent.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.viewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        return binding.root
    }
}