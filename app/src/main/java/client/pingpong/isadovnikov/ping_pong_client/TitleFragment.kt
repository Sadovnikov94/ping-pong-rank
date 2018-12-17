package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false
        )

        binding.startButton.setOnClickListener { it: View ->
            it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        this.buildRankList(binding.playersRank)

        return binding.root
    }

    private fun buildRankList(parentView: ViewGroup) {
        for (player in stupPlayers) {
            val textView = TextView(parentView.context)
            textView.text = player

            parentView.addView(textView)
        }
    }
}
