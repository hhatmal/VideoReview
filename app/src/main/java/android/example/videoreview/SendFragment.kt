package android.example.videoreview

import android.content.Context
import android.content.Intent
import android.example.videoreview.databinding.FragmentSendBinding
import android.example.videoreview.db.ReviewDatabase
import android.example.videoreview.db.ReviewRepository
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_send.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

/**
 * A simple [Fragment] subclass.
 */
class SendFragment : Fragment() {
    private lateinit var binding : FragmentSendBinding
    private lateinit var viewModel : SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val dao = ReviewDatabase.getInstance(this.context).reviewDAO
        val repository = ReviewRepository(dao)
        val factory = SendViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(SendViewModel::class.java)

        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            viewModel.changeStatus()
        })

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, viewModel.toastMessage.value, Toast.LENGTH_SHORT).show()
        })

        binding.viewModel = viewModel

        return binding.root
    }
}
