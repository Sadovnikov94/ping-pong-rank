package client.pingpong.isadovnikov.ping_pong_client

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
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
    private var sortedByRating: Boolean = false
    private var dataList: List<User> = listOf()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchUserBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_user, container, false
        )
        binding.invalidateAll()


        bundle = this.arguments

        sortedByRating = bundle == null

        gson = Gson()
        queue = Volley.newRequestQueue(this.context)
        host = getString(R.string.host)

        this.buildRankList(binding.playersRank)

        binding.searchUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.playersRank.removeAllViews()
                fillList(binding.playersRank, s.toString())
            }
        })

        return binding.root
    }

    private fun buildRankList(parentView: ViewGroup) {
        val request = StringRequest(
                "$host/pingpong/users",
                Response.Listener<String> { response ->
                    val responseObject = gson.fromJson(response, UsersResponse::class.java)
                    this.dataList = if (sortedByRating) {
                        responseObject.users.sortedByDescending { it.rating.toDouble() }
                    } else {
                        responseObject.users
                    }

                    fillList(parentView, ".")

                },
                Response.ErrorListener { it ->
                    val textView = TextView(parentView.context)
                    textView.text = "That didn't work! $it"
                    parentView.addView(textView)
                }
        )

        queue.add(request);
    }

    private fun fillList(parentView: ViewGroup, search: String) {

        this.dataList.filter { it.username.contains(search) }
                .forEach { user ->

                    val userLine = TableRow(parentView.context)

                    val ratingView = TextView(parentView.context)
                    ratingView.setPadding(0, 0, 32, 0)
                    ratingView.text = String.format("%.2f",user.rating.toDouble())
                    ratingView.textSize = 30F
                    userLine.addView(ratingView)

                    val usernameView = TextView(parentView.context)
                    usernameView.text = user.username
                    usernameView.textSize = 30F
                    usernameView.gravity = Gravity.END
                    userLine.addView(usernameView)

                    userLine.setOnClickListener { listener ->
                        bundle?.also {
                            val id = it.getInt(CLICK_OBJECT)

                            when (id) {
                                R.id.playerOneAvatar -> playerOne = user
                                R.id.playerTwoAvatar -> playerTwo = user
                            }

                            listener.findNavController()
                                    .navigate(
                                            R.id.action_userSearchFragment_to_gameConfigurationFragment
                                    )
                        }
                    }

                    userLine.setBackgroundResource(R.drawable.row_border)

                    parentView.addView(userLine)
                }
    }
}
