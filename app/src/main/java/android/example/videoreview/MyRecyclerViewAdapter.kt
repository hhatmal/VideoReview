package android.example.videoreview

import android.example.videoreview.db.Review
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MyRecyclerViewAdapter(var reviewList : List<Review>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)

        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = reviewList[position]
        holder.bind(review)
    }

    fun setData(newData : List<Review>) {
        reviewList = newData
        notifyDataSetChanged()
    }

    fun getReviewAt(position: Int) : Review {
        return reviewList.get(position)
    }
}

class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    fun bind(review: Review) {
        view.name_text_view.text = review.title
        //view.description_text_view.text = review.description
    }
}