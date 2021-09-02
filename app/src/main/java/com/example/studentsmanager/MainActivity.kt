package com.example.studentsmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.studentsmanager.viewmodel.StudentViewModel
import com.example.studentsmanager.viewmodel.StudentViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * App create by Tiến Toàn - 01/09/2021
 * Xây dựng API by Khánh Junior
 *
 * Kiểm tra giữa kì môn Phát triển phần mềm hướng dịch vụ
 *
 * Sử dụng Retrofit - LiveData - Share ViewModel - Navigation Component - DataBinding - Room
 * */

class MainActivity : AppCompatActivity() {

    private lateinit var receiver: BroadcastReceiver

    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainLayout: View = findViewById(R.id.nav_host_fragment)
        if (!isInternetConnected(applicationContext)) {
            Snackbar.make(
                mainLayout,
                getString(R.string.toast_internet_disconnected).toString(),
                Snackbar.LENGTH_SHORT
            )
        }

        val viewModelFactory = StudentViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(StudentViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                when (intent?.action) {
                    ConnectivityManager.CONNECTIVITY_ACTION -> {
                        if (isInternetConnected(context)) {
                            viewModel.fetchStudentsFromNetwork()
                            viewModel.isInternetConnected.value = true
                        } else {
                            viewModel.isInternetConnected.value = false
                        }
                    }
                }
            }
        }

        registerReceiver(receiver, intentFilter)
    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

}