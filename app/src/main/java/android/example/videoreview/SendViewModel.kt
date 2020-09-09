package android.example.videoreview

import android.example.videoreview.db.Review
import android.example.videoreview.db.ReviewRepository
import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.coroutines.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendViewModel(private val repository: ReviewRepository) : ViewModel(), Observable {
    @Bindable
    var email = MutableLiveData<String>()
    private var appExecutors: AppExecutors = AppExecutors()
    var reviews: LiveData<List<Review>> = repository.reviews
    private var isReady = false

    @Bindable
    var toastMessage = MutableLiveData<String>()

    fun changeStatus() {
        isReady = true
    }

    fun sendEmail() : Boolean {
        if(isReady && (email.value.isNullOrEmpty() || !email.value!!.isEmailValid())) {
            toastMessage.value = "Invalid Email!"
            return false
        } else if(isReady) {
            appExecutors.diskIO().execute {
                val props = System.getProperties()
                props.put("mail.smtp.host", "smtp.gmail.com")
                props.put("mail.smtp.socketFactory.port", "465")
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                props.put("mail.smtp.auth", "true")
                props.put("mail.smtp.port", "465")

                val session = Session.getInstance(props,
                    object : javax.mail.Authenticator() {
                        //Authenticating the password
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication(Credentials.EMAIL, Credentials.PASSWORD)
                        }
                    })

                try {
                    //Creating MimeMessage object
                    val mm = MimeMessage(session)
                    val emailId = email.value.toString()
                    //Setting sender address
                    mm.setFrom(InternetAddress(Credentials.EMAIL))
                    //Adding receiver
                    mm.addRecipient(
                        Message.RecipientType.TO,
                        InternetAddress(emailId)
                    )
                    //Adding subject
                    mm.subject = "Reviews"

                    //Adding message
                    mm.setText(reviewsToString())

                    //Sending email
                    Transport.send(mm)

                } catch (e: MessagingException) {
                    e.printStackTrace()
                }
            }

            toastMessage.value = "Email has been sent!"
            return true
        }
        return false
    }

    fun reviewsToString() : String {
        var reviewString  = "List Of Reviews: \n"
        for(review in reviews.value!!) {
            reviewString = reviewString.plus("Title: ").plus(review.title).plus("\n")
            reviewString = reviewString.plus("Description: ").plus(review.rating).plus("\n")
            reviewString = reviewString.plus("Rating: ").plus(review.description).plus("\n")
        }

        return reviewString
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}