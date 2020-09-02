package android.example.videoreview.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Review::class], version = 2 )
abstract class ReviewDatabase : RoomDatabase() {

    abstract val reviewDAO : ReviewDAO

    companion object {
        @Volatile
        private var INSTANCE: ReviewDatabase? = null

        fun getInstance(context: Context?): ReviewDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    if (context != null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ReviewDatabase::class.java,
                            "review_data_database"
                        ).build()
                    }
                }
                return instance!!
            }
        }
    }
}