package android.example.videoreview

import android.example.videoreview.db.ReviewRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddReviewViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddReviewViewModel::class.java)) {
            return AddReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
