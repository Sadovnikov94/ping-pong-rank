package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentGameConfigurationBinding

val CLICK_OBJECT: String = "clickObject"

class GameConfigurationFragment : Fragment() {

    private lateinit var binding: FragmentGameConfigurationBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_configuration, container, false
        )

        playerOne?.also {
            binding.playerOneName.text = it.username
        }

        playerTwo?.also {
            binding.playerTwoName.text = it.username
        }


        binding.startButton.isEnabled = (playerOne != null && playerTwo != null) && (playerOne!!.username != playerTwo!!.username)

        binding.startButton.setOnClickListener { it: View ->
            it.findNavController().navigate(R.id.action_gameConfigurationFragment_to_gameFragment)
        }

        binding.playerOneAvatar.setOnClickListener { it: View ->
            val bundle = Bundle()
            bundle.putInt(CLICK_OBJECT, binding.playerOneAvatar.id)

            it.findNavController()
                    .navigate(R.id.action_gameConfigurationFragment_to_userSearchFragment, bundle)
        }

        binding.playerTwoAvatar.setOnClickListener { it: View ->
            val bundle = Bundle()
            bundle.putInt(CLICK_OBJECT, binding.playerTwoAvatar.id)

            it.findNavController()
                    .navigate(R.id.action_gameConfigurationFragment_to_userSearchFragment, bundle)
        }

        return binding.root
    }
}
