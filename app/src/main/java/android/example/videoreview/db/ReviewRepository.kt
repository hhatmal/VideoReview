package android.example.videoreview.db

import androidx.lifecycle.LiveData

class ReviewRepository(private val dao : ReviewDAO) {
    val reviews = dao.getAllReviews()

    suspend fun insert(review : Review) {
        dao.insertReview(review)
    }
}