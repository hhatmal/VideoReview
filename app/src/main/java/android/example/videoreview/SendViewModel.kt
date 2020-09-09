package android.example.videoreview

import android.example.videoreview.db.Review
import android.example.videoreview.db.ReviewRepository
import android.text.TextUtils
import android.util.Log
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
    private val email = MutableLiveData<String>()
    @Bindable
    private val toastMessage = MutableLiveData<String>()
    private val appExecutors: AppExecutors = AppExecutors()
    private val reviews = repository.reviews
    private var isReady = false

    val emailValue: MutableLiveData<String> get() = email
    val toastMessageValue: MutableLiveData<String> get() = toastMessage
    val appExecutorsData: AppExecutors get() = appExecutors
    val reviewsData: LiveData<List<Review>> get() = reviews
    val isReadyData: Boolean get() = isReady

    init {
        email.value = ""
    }

    fun changeStatus() {
        isReady = true
    }

    fun sendEmail() : Boolean {
        if(isValidEmail(email.value!!) && isReady) {
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
        } else {
            toastMessage.value = "Unable to send email!"
        }
        return false
    }

    private fun reviewsToString() : String {
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

    private fun isValidEmail(emailData : String) : Boolean {
        return !TextUtils.isEmpty(emailData) && android.util.Patterns.EMAIL_ADDRESS.matcher(emailData).matches();
    }
}