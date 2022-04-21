package com.ssafy.withssafy.src.main.board

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssafy.withssafy.R
import com.ssafy.withssafy.src.main.MainActivity


class BoardJobFragment : Fragment() {
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}