package com.example.studentsmanager.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studentsmanager.Student
import com.example.studentsmanager.network.StudentApi
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {
    private var _arrayStudent = MutableLiveData<List<Student>>()
    val arrayStudent
        get() = _arrayStudent

    private var _status = MutableLiveData<String>()
    val status
        get() = _status

    // Using coroutine get list student from network
    private fun getStudents() {
        viewModelScope.launch {
            try {
                _arrayStudent.value = StudentApi.retrofit.getStudents()
                Log.d("ToanNTe", "getStudents: ${_arrayStudent.value?.size}")
                _status.value = "success"
            } catch (e: Exception) {
                _arrayStudent.value = listOf()
                Log.d("ToanNTe", "getStudents: $e")
                _status.value = "error"
            }
        }
    }

    init {
        getStudents()
    }
}

class StudentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}