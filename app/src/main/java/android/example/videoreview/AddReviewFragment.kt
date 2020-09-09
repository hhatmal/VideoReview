package android.example.videoreview

import android.example.videoreview.databinding.FragmentAddReviewBinding
import android.example.videoreview.db.ReviewDatabase
import android.example.videoreview.db.ReviewRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController

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

        viewModel.validInputValue.observe(viewLifecycleOwner, Observer { validInput ->
            validInput?.let {
                if(!validInput)
                    Toast.makeText(activity, "Invalid input!", Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(activity, "Review has been added!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root).navigate(R.id.action_addReview_to_homeFragment)
                }

                viewModel.validInputValue.value = null
            }
        })
        binding.viewModel = viewModel

        binding.cancelButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_addReview_to_homeFragment)
        }

        return binding.root
    }

}
