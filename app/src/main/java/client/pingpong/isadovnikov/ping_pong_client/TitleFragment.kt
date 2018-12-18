package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentTitleBinding
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class TitleFragment : Fragment() {
    private val HOST: String = "http://192.168.1.34:8080"

    private lateinit var queue: RequestQueue
    private lateinit var gson: Gson

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false
        )

        gson = Gson()
        queue = Volley.newRequestQueue(this.context)

        binding.startButton.setOnClickListener { it: View ->
            it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        return binding.root
    }

}
