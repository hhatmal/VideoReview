package android.example.videoreview

import android.example.videoreview.db.Review
import android.example.videoreview.db.ReviewRepository
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddReviewViewModel(private val repository: ReviewRepository) : ViewModel() {
    var title = MutableLiveData<String>()
    var rating = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    fun insert() {
        viewModelScope.launch {
            repository.insert(Review(title.toString(), rating.toString(), description.toString()))
        }
    }
}