package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.findNavController
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentSearchUserBinding
import client.pingpong.isadovnikov.ping_pong_client.databinding.FragmentTitleBinding
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class UserSearchFragment : Fragment() {
    private val HOST: String = "http://192.168.1.34:8080"

    private lateinit var queue: RequestQueue
    private lateinit var gson: Gson

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchUserBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_user, container, false
        )

        gson = Gson()
        queue = Volley.newRequestQueue(this.context)

        this.buildRankList(binding.playersRank)

        return binding.root
    }

    private fun buildRankList(parentView: ViewGroup) {
        var number = 1
        val request = StringRequest(
            "$HOST/pingpong/users",
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

                    parentView.addView(userLine)
                    number++;
                }

            },
            Response.ErrorListener { it ->
                val textView = TextView(this.context)
                textView.text = "That didn't work! $it"
                parentView.addView(textView)
            }
        )

        queue.add(request);
    }
}

data class UsersResponse(
    val users: List<User>
)

data class User(
    val username: String,
    val rating: String
)