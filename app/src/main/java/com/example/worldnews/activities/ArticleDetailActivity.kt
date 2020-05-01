package com.example.worldnews

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_article_detail.*

// this activity will show the article
class ArticleDetailActivity : AppCompatActivity() {

    // properties
    var url : String? = null
    var articleTitle : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        // get intent extras
        val bundle = intent.extras

        bundle?.let {
            url = it.getString("url")
            articleTitle = it.getString("title")
        }

        // load article URL in the webView
        url?.let {
            webView_article.loadUrl(it)
        }

        //set the article title
        articleTitle?.let {
            title = it
        }

        // set my custom WebViewClient
        webView_article.webViewClient = MyWebClient()
    }

    // when creating the options menu (top bar)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflate this inside
        menuInflater.inflate(R.menu.menu_article, menu)
        return true
    }

    // Set behaviour for when an item is clicked in the top bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // when action_share is clicked
        if (item.itemId == R.id.action_share) {

            // create intent for sharing data
            val shareIntent = Intent(Intent.ACTION_SEND)
            // set the type of data
            shareIntent.type = "text/plain"
            // set the "subject" extra
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, articleTitle)
            // set the "text" extra with the article URL
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            // start the activity
            startActivity(Intent.createChooser(shareIntent, "Latest News Share"))

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // custom WebViewClient to load everything inside the webView
    inner class MyWebClient : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            request?.let {
                view?.loadUrl(it.url.toString())
            }

            return true
        }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                view?.loadUrl(it)
            }
            return true
        }


    }
}
