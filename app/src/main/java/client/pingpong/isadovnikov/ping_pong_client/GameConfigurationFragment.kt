package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentGameConfigurationBinding

class GameConfigurationFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameConfigurationBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_configuration, container, false
        )
        binding.invalidateAll()


        binding.startButton.setOnClickListener { it: View ->
            it.findNavController().navigate(R.id.action_gameConfigurationFragment_to_gameFragment)
        }

        return binding.root
    }
}
