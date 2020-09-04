package android.example.videoreview

import android.example.videoreview.db.ReviewRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SendViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SendViewModel::class.java)) {
            return SendViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
