package com.example.worldnews

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.list_layout.*
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset

class News : AppCompatActivity() {

    // properties
    val articles : MutableList<Article> = ArrayList<Article>()
    val context = this
    //var countryPath = ""
    var categoryPath = ""
    //var countryPT = ""
    var categoryWord = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set the content to be the one in the list_layout XML file
        setContentView(R.layout.list_layout)

        // get intent extras
        val bundle = intent.extras
        //countryPath = bundle!!.getString("country")!!
        categoryPath = bundle!!.getString("categoryCode")!!
        //countryPT = bundle.getString("countryWord")!!
        categoryWord = bundle.getString("categoryWord")!!


        // write title of page
        tv_title.text = categoryWord

        // create and set the adapter to the list view
        val articlesAdapter = ArticlesAdapter(this, articles as ArrayList<Article>)
        list_view.adapter = articlesAdapter

        // async task to load news
        val loadNewsAsync = object : AsyncTask<Void, Void, String>(){

            override fun doInBackground(vararg params: Void?): String {

                // get URL for API call
                val url = URL(MainActivity.BASE_API + MainActivity.PATH + categoryPath + MainActivity.API_KEY)

                // get URL content (JSON)
                val urlContent = url.readText(Charset.defaultCharset())
                Log.d("latestnews", urlContent)


                return urlContent
            }

            override fun onPostExecute(urlContent: String) {
                super.onPostExecute(urlContent)

                // turn URL content into a JSON object
                val jsonResult = JSONObject(urlContent)
                // get the "status" value from the JSON object
                var jsonOk = jsonResult.getString("status")
                // and if it's "ok"
                if (jsonOk.compareTo("ok") == 0) {

                    // get the array of articles from the JSON object
                    val articleJsonArray = jsonResult.getJSONArray("articles")

                    //for the number of articles in the JSON array
                    for (index in 0 until articleJsonArray.length()) {
                        // get the JSON article
                        val jsonArticle = articleJsonArray[index] as JSONObject
                        // parse the JSON article into an Article object and add it to the articles array
                        articles.add(Article.parseJson(jsonArticle))
                    }

                }
                // if it's not "ok"
                else {
                    // make a toast with an error message
                    Toast.makeText(context, "Erro ao descarregar notícias", Toast.LENGTH_LONG).show()
                }
                // notify the adapter that the data has changed so that it will refresh
                articlesAdapter.notifyDataSetChanged()
            }
        }

        // load news
        loadNewsAsync.execute(null, null, null)

    }
}
