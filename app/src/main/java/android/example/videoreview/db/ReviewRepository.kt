package android.example.videoreview.db

class ReviewRepository(private val dao : ReviewDAO) {

    suspend fun insert(review : Review) {
        dao.insertReview(review)
    }
}