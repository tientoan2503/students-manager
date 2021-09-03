package com.example.studentsmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.studentsmanager.R
import com.example.studentsmanager.Student
import com.example.studentsmanager.databinding.AddStudentFragmentBinding
import com.example.studentsmanager.viewmodel.StudentViewModel
import com.example.studentsmanager.viewmodel.StudentViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class AddStudentFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: AddStudentFragmentBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddStudentFragmentBinding.inflate(inflater, container, false)
        binding.addStudentFragment = this

        val viewModelFactory = StudentViewModelFactory()
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(StudentViewModel::class.java)
        navController = findNavController()

        return binding.root
    }

    // Click button huy
    fun cancel() {
        navController.navigate(R.id.action_addStudentFragment_to_studentsFragment)
    }

    // Click button them
    fun addStudent() {
        var isFilled = false
        val id = binding.edtIdSv.text.toString()
        val name = binding.edtNameSv.text.toString()
        val email = binding.edtEmailSv.text.toString()
        val phone = binding.edtPhoneSv.text.toString()
        isFilled = isTextFieldFilled(binding.edtIdSvField, id != "")
        isFilled = isTextFieldFilled(binding.edtNameField, name != "")
        isFilled = isTextFieldFilled(binding.edtPhoneField, phone != "")
        isFilled = isTextFieldFilled(binding.edtEmailField, email != "")
        if (isFilled) {
            val student = Student(id, name, phone, email)
            if (viewModel.isInternetConnected.value == true) {
                viewModel.createStudent(requireContext(), student)
                // Them xong back ve
                cancel()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.toast_internet_disconnected),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    // Thông báo việc chưa nhập vào dữ liệu
    private fun isTextFieldFilled(textField: TextInputLayout, isFilled: Boolean): Boolean {
        if (isFilled) {
            textField.isErrorEnabled = false
            textField.error = null
            return true
        }
        textField.isErrorEnabled = true
        textField.error = getString(R.string.please_fill_information)
        return false
    }
}