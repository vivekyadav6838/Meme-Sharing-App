package com.example.memeshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.android.volley.Response as Response

class MainActivity : AppCompatActivity() {
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Memeload()
    }

    private fun Memeload(){
        val queue = Volley.newRequestQueue(this)
        url = "https://meme-api.com/gimme"



// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response->
                val url = response.getString("url")

                Glide.with(this).load(url).into(findViewById(R.id.memeView))

            },
            Response.ErrorListener {

            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun Shareme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey bhai log chekout this meme $url")
        val chooser = Intent.createChooser(intent,"Share this via")
        startActivity(chooser)
    }
    fun Nextme(view: View) {
        Memeload()
    }
}