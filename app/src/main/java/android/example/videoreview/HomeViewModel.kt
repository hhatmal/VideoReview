package android.example.videoreview

import android.example.videoreview.db.Review
import android.example.videoreview.db.ReviewRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ReviewRepository) : ViewModel() {

    fun getReviews() : LiveData<List<Review>> {
        return repository.reviews
    }

    fun deleteReview(review : Review) {
        viewModelScope.launch {
            repository.delete(review)
        }
    }
}
