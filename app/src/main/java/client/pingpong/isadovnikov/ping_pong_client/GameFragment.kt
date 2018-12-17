package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentSetupGameBinding

class GameFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSetupGameBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_setup_game, container, false
        )

        return binding.root
    }
}
