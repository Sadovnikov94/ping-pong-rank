package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        )
        binding.invalidateAll()


        binding.playerOneAvatar.setOnClickListener { it: View ->
            it.findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
        }

        binding.playerTwoAvatar.setOnClickListener { it: View ->
            it.findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
        }


        return binding.root
    }
}
