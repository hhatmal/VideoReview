package android.example.videoreview

import android.example.videoreview.db.Review
import android.example.videoreview.db.ReviewRepository
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class AddReviewViewModel(private val repository: ReviewRepository) : ViewModel(), Observable {
    @Bindable
    var title = MutableLiveData<String>()

    @Bindable
    var rating = MutableLiveData<String>()

    @Bindable
    var description = MutableLiveData<String>()

    val titleData : LiveData<String>
        get() = title

    val ratingData : LiveData<String>
        get() = rating

    val descriptionData : LiveData<String>
        get() = description

    fun insert() {
        Log.w("vince", "message" + title.value)
        viewModelScope.launch {
            repository.insert(Review(
                title.value!!,
                rating.value!!,
                description.value!!))
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}