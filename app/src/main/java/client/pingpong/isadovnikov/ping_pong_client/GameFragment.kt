package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentGameBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject


class GameFragment : Fragment() {
    private lateinit var host: String

    private lateinit var binding: FragmentGameBinding
    private lateinit var queue: RequestQueue
    private lateinit var gson: Gson

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game, container, false
        )
        binding.invalidateAll()

        gson = Gson()
        queue = Volley.newRequestQueue(this.context)
        host = getString(R.string.host)

        binding.playerOneUsername.text = playerOne?.username
        binding.playerTwoUsername.text = playerTwo?.username

        binding.playerOneAvatar.setOnClickListener { it: View ->
            completeGame(playerOne!!, playerTwo!!, it)
        }

        binding.playerTwoAvatar.setOnClickListener { it: View ->
            completeGame(playerTwo!!, playerOne!!, it)
        }


        return binding.root
    }


    private fun completeGame(winner: User, looser: User, view: View) {

        val game = Game(winner, looser)

        val request = JsonObjectRequest(
                Request.Method.POST,
                "$host/pingpong/games",
                JSONObject(gson.toJson(game)),
                Response.Listener { response ->
                    view.findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
                },
                Response.ErrorListener { error ->
                    view.findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
                }
        )
        queue.add(request)

    }
}

data class Game(
        val winner: User,
        val looser: User
)
