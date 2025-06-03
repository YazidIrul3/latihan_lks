package com.example.day38

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.day38.api.ApiClient
import com.example.day38.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var nameTxt :TextView
    private lateinit var usernameTxt :TextView
    private lateinit var emailTxt :TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_profile, container, false)

        emailTxt = view.findViewById(R.id.email)
        nameTxt = view.findViewById(R.id.name)
        usernameTxt = view.findViewById(R.id.username)
        sessionManager = context?.let { SessionManager(it) }!!

        val token = sessionManager.getToken()

        if (token != null) {
            profile(token)
        }

        return view
    }

    private fun profile(token : String) {
        ApiClient.getApiService.profile(token)
            .enqueue(object  : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val user = response.body()

                    if(user != null) {
                        nameTxt.text = user.firstName + "" + user.lastName
                        usernameTxt.text = user.username
                        emailTxt.text = user.email
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                   Toast.makeText(context,t.localizedMessage,Toast.LENGTH_SHORT).show()
                }

            })
    }

}