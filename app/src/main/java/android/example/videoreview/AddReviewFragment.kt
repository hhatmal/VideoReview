package android.example.videoreview

import android.example.videoreview.databinding.FragmentAddReviewBinding
import android.example.videoreview.databinding.FragmentHomeBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
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
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(AddReviewViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.createButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_addReview_to_homeFragment)
        }
        binding.cancelButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_addReview_to_homeFragment)
        }

        return binding.root
    }

}
