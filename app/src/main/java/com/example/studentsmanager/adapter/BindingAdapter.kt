package com.example.studentsmanager.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.studentsmanager.R
import com.example.studentsmanager.viewmodel.StudentApiStatus

@BindingAdapter("fetchDataStatus")
fun ImageView.bindingStatus(status: StudentApiStatus?) {
    when (status) {
        StudentApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        StudentApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_disconnected_internet)
        }
        StudentApiStatus.DONE -> {
            visibility = View.GONE
        }
    }
}