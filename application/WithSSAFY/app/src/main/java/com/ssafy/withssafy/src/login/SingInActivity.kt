package com.ssafy.withssafy.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivitySingInBinding
import com.ssafy.withssafy.src.main.MainActivity
import com.ssafy.withssafy.src.viewmodel.UserViewModel
import kotlinx.coroutines.runBlocking
import java.security.DigestException
import java.security.MessageDigest

class SingInActivity : BaseActivity<ActivitySingInBinding>(ActivitySingInBinding::inflate) {
    private val TAG = "SingInActivity_싸피"
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        runBlocking {
            userViewModel.getAllUserList()
        }

        Log.d(TAG, "onCreate: ${userViewModel.allUserList.value}")

        val userId = ApplicationClass.sharedPreferencesUtil.getAutoLogin()
        if (userId != null && userId != -1){
            runBlocking {
                userViewModel.getUser(userId,1)
            }
            if(userViewModel.isAutoLoginPossible.value == 1) {
                openFragment(1)
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.login_frame_layout, SingInFragment())
                    .commit()
            }

        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame_layout, SingInFragment())
                .commit()
        }

        Log.d(TAG, "onCreate: ${sha256("test")}\n${sha256("admin")}")
    }
    fun openFragment(int : Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            1->{
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            2->{
                transaction.replace(R.id.login_frame_layout, SingInFragment())
                    .addToBackStack(null)
            }
            3->{
                transaction.replace(R.id.login_frame_layout, SignUpFragment())
                    .addToBackStack(null)
            }
        }
        transaction.commit()
    }

    fun sha256(pw: String) : String {
        val hash: ByteArray
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(pw.toByteArray())
            hash = md.digest()
        } catch (e: CloneNotSupportedException) {
            throw DigestException("couldn't make digest of partial content");
        }

        return Base64.encodeToString(hash, Base64.NO_WRAP)
    }
}