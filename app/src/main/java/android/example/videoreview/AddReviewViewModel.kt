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
    private val title = MutableLiveData<String>()
    @Bindable
    private val rating = MutableLiveData<String>()
    @Bindable
    private val description = MutableLiveData<String>()
    private val validInput = MutableLiveData<Boolean?>()

    val titleValue: MutableLiveData<String> get() = title
    val ratingValue: MutableLiveData<String> get() = rating
    val descriptionValue: MutableLiveData<String> get() = description
    val validInputValue: MutableLiveData<Boolean?> get() = validInput

    fun insert() {
        if(isValid()) {
            viewModelScope.launch {
                repository.insert(
                    Review(
                        title.value!!,
                        rating.value!!,
                        description.value!!
                    )
                )
            }
        }
    }

    // check if empty fields -> send toast
    private fun isValid() : Boolean {
        if((title.value.isNullOrEmpty() || description.value.isNullOrEmpty()
                || rating.value.isNullOrEmpty())) {

            validInput.value = false
            return false
        }

        validInput.value = true
        return true
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}