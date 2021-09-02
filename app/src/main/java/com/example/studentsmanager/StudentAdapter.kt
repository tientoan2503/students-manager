package com.example.studentsmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsmanager.databinding.ListItemStudentBinding

class StudentAdapter: ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
       return StudentViewHolder.createHolder(parent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }

    class StudentViewHolder(private val binding: ListItemStudentBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun createHolder(parent: ViewGroup): StudentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemStudentBinding.inflate(layoutInflater, parent, false)
                return StudentViewHolder(binding)
            }
        }

        fun bind(student: Student) {
            binding.student = student
            binding.executePendingBindings()
        }
    }
}

class StudentDiffCallback: DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.studentCode == newItem.studentCode
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }

}