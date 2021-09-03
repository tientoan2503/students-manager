package com.example.studentsmanager.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studentsmanager.R
import com.example.studentsmanager.StudentAdapter
import com.example.studentsmanager.databinding.StudentsFragmentBinding
import com.example.studentsmanager.viewmodel.StudentViewModel
import com.example.studentsmanager.viewmodel.StudentViewModelFactory
import com.google.android.material.snackbar.Snackbar

class StudentsFragment : Fragment() {
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = StudentsFragmentBinding.inflate(inflater, container, false)
        val viewModelFactory = StudentViewModelFactory()
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(StudentViewModel::class.java)

        val adapter = StudentAdapter()
        binding.recyclerviewStudents.adapter = adapter
        viewModel.arrayStudent.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.isInternetConnected.observe(viewLifecycleOwner, Observer {
            if (viewModel.isInternetConnected.value == false) {
                Snackbar.make(
                    binding.root,
                    context?.getString(R.string.toast_internet_disconnected).toString(),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        binding.viewModel = viewModel
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val navController = findNavController()

        binding.fabAddStudent.setOnClickListener {
            navController.navigate(R.id.action_studentsFragment_to_addStudentFragment)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    // Thêm menu search
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.queryHint = "Nhập tên sinh viên"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchStudent(query!!)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}