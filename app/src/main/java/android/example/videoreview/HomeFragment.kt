package android.example.videoreview

import android.example.videoreview.databinding.FragmentHomeBinding
import android.example.videoreview.db.Review
import android.example.videoreview.db.ReviewDatabase
import android.example.videoreview.db.ReviewRepository
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        my_recycler_view.layoutManager = LinearLayoutManager(this.context)
        my_recycler_view.adapter = MyRecyclerViewAdapter(listOf())
        viewModel.getReviews().observe(viewLifecycleOwner, Observer { review ->
            (my_recycler_view.adapter as MyRecyclerViewAdapter).setData(review)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addReview)
        }

        val dao = ReviewDatabase.getInstance(this.context).reviewDAO
        val repository = ReviewRepository(dao)
        val factory = HomeViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}
