package com.ssafy.withssafy.src.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.WindowManager
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.ApplicationClass
import com.ssafy.withssafy.config.BaseActivity
import com.ssafy.withssafy.databinding.ActivitySingInBinding
import com.ssafy.withssafy.src.main.MainActivity
import kotlinx.coroutines.runBlocking
import java.security.DigestException
import java.security.MessageDigest

class SingInActivity : BaseActivity<ActivitySingInBinding>(ActivitySingInBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_sing_in)
//        val userId = ApplicationClass.sharedPreferencesUtil.getAutoLogin()

//        if (userId != null || userId != -1){
//            var isPossible = 0
//            runBlocking {
//                isPossible = mainViewModel.getUserInfo(userId, true)
//            }
//            if(isPossible == 1) {
//                openFragment(1)
//            } else {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.login_frame_layout, SignInFragment())
//                    .commit()
//            }
//
//        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.login_frame_layout, SingInFragment())
                .commit()
//        }
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