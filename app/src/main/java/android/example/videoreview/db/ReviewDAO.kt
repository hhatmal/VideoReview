package android.example.videoreview.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ReviewDAO {

    @Insert
    suspend fun insertReview(review : Review)

    @Query("SELECT * FROM review_data_table")
    fun getAllReviews() : LiveData<List<Review>>

    @Delete
    suspend fun deleteReview(review : Review)
}