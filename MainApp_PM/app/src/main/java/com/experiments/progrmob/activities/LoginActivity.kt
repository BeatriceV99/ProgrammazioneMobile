package com.experiments.progrmob.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.experiments.progrmob.R
import com.experiments.progrmob.fragments.DayListFragment
import com.experiments.progrmob.fragments.LogInFragment
import com.experiments.progrmob.fragments.SignUpFragment

class LoginActivity : AppCompatActivity() {
    var isLogin : Boolean = false
    var fragmentManager : FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Render fragment
        this.fragmentManager = this.getSupportFragmentManager()
        renderFragment();
    }

    private fun renderFragment() {
        val frag : Fragment
        if (isLogin) {
            // Login
            frag = LogInFragment()
        } else {
            // Creation
            frag = SignUpFragment()
        }

        fragmentManager!!.commit {
            setReorderingAllowed(true)
            this.replace(R.id.loginRegisterFragment, frag)
        }
    }

    fun switchFragment() {
        isLogin = !isLogin
        renderFragment()
    }
}