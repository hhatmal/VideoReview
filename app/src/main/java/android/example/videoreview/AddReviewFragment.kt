package android.example.videoreview

import android.example.videoreview.databinding.FragmentAddReviewBinding
import android.example.videoreview.db.ReviewDatabase
import android.example.videoreview.db.ReviewRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 */
class AddReviewFragment : Fragment() {
    private lateinit var binding : FragmentAddReviewBinding
    private lateinit var viewModel : AddReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_review, container, false)

        val dao = ReviewDatabase.getInstance(this.context).reviewDAO
        val repository = ReviewRepository(dao)
        val factory = AddReviewViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(AddReviewViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//        binding.createButton.setOnClickListener {
//            it.findNavController().navigate(R.id.action_addReview_to_homeFragment)
//        }
        binding.cancelButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_addReview_to_homeFragment)
        }

        return binding.root
    }

}
