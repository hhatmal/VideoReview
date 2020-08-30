package android.example.videoreview.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_data_table")
data class Review(

    @ColumnInfo(name = "title")
    val title:String,

    @ColumnInfo(name = "rating")
    val rating:String,

    @ColumnInfo(name = "description")
    val description:String
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}