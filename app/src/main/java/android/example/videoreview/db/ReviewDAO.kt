package android.example.videoreview.db

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface ReviewDAO {

    @Insert
    suspend fun insertReview(review : Review) : Long
}