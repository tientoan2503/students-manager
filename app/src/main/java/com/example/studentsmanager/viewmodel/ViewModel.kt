package com.example.studentsmanager.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.studentsmanager.Student
import com.example.studentsmanager.network.StudentApi
import kotlinx.coroutines.launch

/**
Class biểu thị trạng thái lấy dữ liệu từ network
 */
enum class StudentApiStatus {
    LOADING,
    ERROR,
    DONE
}

class StudentViewModel : ViewModel() {
    private var _arrayStudent = MutableLiveData<List<Student>>()
    val arrayStudent: LiveData<List<Student>> = _arrayStudent

    private var _status = MutableLiveData<StudentApiStatus>()
    val status: LiveData<StudentApiStatus> = _status

    private var _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected = _isInternetConnected

    // Using coroutine get list student from network
    fun fetchStudentsFromNetwork() {
        viewModelScope.launch {
            _status.value = StudentApiStatus.LOADING
            try {
                _arrayStudent.value = StudentApi.retrofit.getStudents()
                _status.value = StudentApiStatus.DONE
            } catch (e: Exception) {
                _status.value = StudentApiStatus.ERROR
                _arrayStudent.value = listOf()
            }
        }
    }

    fun createStudent(student: Student) {
        viewModelScope.launch {
            try {
                val response = StudentApi.retrofit.createStudent(student)
                Log.d("ToanNTe", "createStudent: ${response.body()}")
                Log.d("ToanNTe", "createStudent: ${response.code()}")
                Log.d("ToanNTe", "createStudent: ${response.message()}")
            } catch (e: Exception) {
                Log.d("ToanNTe", "createStudent: $e")
            }
        }
    }

    init {
        fetchStudentsFromNetwork()
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