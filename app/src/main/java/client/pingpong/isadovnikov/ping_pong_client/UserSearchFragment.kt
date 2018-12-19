package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentSearchUserBinding
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class UserSearchFragment : Fragment() {

    private lateinit var host: String
    private lateinit var queue: RequestQueue
    private lateinit var gson: Gson
    private var bundle: Bundle? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchUserBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_user, container, false
        )
        binding.invalidateAll()

        bundle = this.arguments
        gson = Gson()
        queue = Volley.newRequestQueue(this.context)
        host = getString(R.string.host)

        this.buildRankList(binding.playersRank)

        return binding.root
    }

    private fun buildRankList(parentView: ViewGroup) {
        var number = 1
        val request = StringRequest(
            "$host/pingpong/users",
            Response.Listener<String> { response ->
                val responseObject = gson.fromJson(response, UsersResponse::class.java)
                for (user in responseObject.users) {

                    val userLine = TableRow(parentView.context)

                    val positionView = TextView(parentView.context)
                    positionView.text = number.toString()
                    positionView.textSize = 30F
                    positionView.gravity = Gravity.START
                    userLine.addView(positionView)

                    val ratingView = TextView(parentView.context)
                    ratingView.text = user.rating
                    ratingView.textSize = 30F
                    ratingView.gravity = Gravity.START
                    userLine.addView(ratingView)

                    val usernameView = TextView(parentView.context)
                    usernameView.text = user.username
                    usernameView.textSize = 30F
                    usernameView.gravity = Gravity.END
                    userLine.addView(usernameView)

                    userLine.setOnClickListener {listener ->
                        bundle?.also {
                            val id = it.getInt(CLICK_OBJECT)

                            when(id) {
                                R.id.playerOneAvatar -> playerOne = user
                                R.id.playerTwoAvatar -> playerTwo = user
                            }

                            listener.findNavController()
                                    .navigate(
                                            R.id.action_userSearchFragment_to_gameConfigurationFragment
                                    )
                        }
                    }

                    parentView.addView(userLine)
                    number++;
                }

            },
            Response.ErrorListener { it ->
                val textView = TextView(parentView.context)
                textView.text = "That didn't work! $it"
                parentView.addView(textView)
            }
        )

        queue.add(request);
    }
}
