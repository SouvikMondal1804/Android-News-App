package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<NewsArticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRecyclerView = findViewById(R.id.newsRecyclerView)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)

        // adapter set with empty list first
        newsAdapter = NewsAdapter(newsList) { article ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("url", article.url)
            startActivity(intent)
        }

        newsRecyclerView.adapter = newsAdapter

        fetchNews()
    }

    private fun fetchNews() {
        RetrofitInstance.api.searchNews(
            query = "india",
            apiKey = "0a44ebfd577c422bb1e427f844ca26eb"
        ).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    newsList.clear()
                    newsList.addAll(articles)
                    newsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Response failed: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}