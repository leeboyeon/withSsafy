package com.ssafy.withssafy.src.main.team

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.ssafy.withssafy.R
import com.ssafy.withssafy.config.BaseFragment
import com.ssafy.withssafy.databinding.FragmentTeamDetailBinding

class TeamDetailFragment : BaseFragment<FragmentTeamDetailBinding>(FragmentTeamDetailBinding::bind, R.layout.fragment_team_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    private fun setListener(){
        initButtons()
    }
    private fun initButtons(){
        binding.fragmentTeamDetailRequest.setOnClickListener {
            showRequestDialog()
        }
    }
    private fun showRequestDialog(){
        var dialog = Dialog(requireContext())
        var dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_team_request,null)
        if(dialogView.parent!=null){
            (dialogView.parent as ViewGroup).removeView(dialogView)
        }

        dialog.setContentView(dialogView)
        dialog.show()

        dialogView.findViewById<AppCompatButton>(R.id.fragment_team_requestCancle).setOnClickListener {
            dialog.dismiss()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}