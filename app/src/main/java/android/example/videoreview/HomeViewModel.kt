package android.example.videoreview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private lateinit var reviewList : LiveData<List<Review>>
    val reviewListData : LiveData<List<Review>>
    get() = reviewList

}