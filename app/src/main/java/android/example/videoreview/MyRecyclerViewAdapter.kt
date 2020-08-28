package android.example.videoreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MyRecyclerViewAdapter(val reviewList:List<Review>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)

        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = reviewList[position]
        holder.bind(fruit)
    }
}

class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    fun bind(review: Review) {
        view.name_text_view.text = review.title
    }
}