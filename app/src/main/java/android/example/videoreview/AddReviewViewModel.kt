package android.example.videoreview

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddReviewViewModel : ViewModel() {
    var title = MutableLiveData<String>()
    var rating = MutableLiveData<String>()
    var description = MutableLiveData<String>()
}