package com.example.memeshareapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private var currentMemeUrl: String? = null
    private lateinit var memeImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeImageView = findViewById(R.id.memeView)
        loadMeme()
    }

    private fun loadMeme() {
        val queue = Volley.newRequestQueue(this)
        val apiUrl = "https://meme-api.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            apiUrl,
            null,
            { response ->
                currentMemeUrl = response.getString("url")
                Glide.with(this)
                    .load(currentMemeUrl)
                    .into(memeImageView)
            },
            {
                Toast.makeText(
                    this,
                    "Failed to load meme. Check internet.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        queue.add(jsonObjectRequest)
    }

    fun Shareme(view: View) {
        if (currentMemeUrl == null) {
            Toast.makeText(this, "No meme to share yet", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "😂 Check out this meme!\n$currentMemeUrl"
        )

        startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun Nextme(view: View) {
        loadMeme()
    }
}
