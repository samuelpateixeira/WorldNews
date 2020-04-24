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

    val articles : MutableList<Article> = ArrayList<Article>()
    val context = this
    var countryPath = ""
    var categoryPath = ""
    var countryPT = ""
    var categoryPT = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_layout)

        val bundle = intent.extras

        countryPath = bundle!!.getString("country")!!
        categoryPath = bundle.getString("category")!!
        countryPT = bundle.getString("countryPT")!!
        categoryPT = bundle.getString("categoryPT")!!


        //title = categoryPT + " em " + countryPT
        tv_title.text = categoryPT + " em " + countryPT

        val articlesAdapter = ArticlesAdapter(this, articles as ArrayList<Article>)
        list_view.adapter = articlesAdapter

        val asyncTask = object : AsyncTask<Void, Void, String>(){
            override fun doInBackground(vararg params: Void?): String {

                val url = URL(MainActivity.BASE_API + MainActivity.PATH + countryPath + categoryPath + MainActivity.API_KEY)

                val urlContent = url.readText(Charset.defaultCharset())
                Log.d("latestnews", urlContent)


                return urlContent
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)

                val jsonResult = JSONObject(result)
                var jsonOk = jsonResult.getString("status")
                if (jsonOk.compareTo("ok") == 0) {
                    //Toast.makeText(context, "boas notícias", Toast.LENGTH_LONG).show()

                    val articleJsonArray = jsonResult.getJSONArray("articles")

                    for (index in 0 until articleJsonArray.length()) {
                        val jsonArticle = articleJsonArray[index] as JSONObject
                        articles.add(Article.parseJson(jsonArticle))
                        //load image
                    }

                }
                else {
                    Toast.makeText(context, "Erro ao descarregar notícias", Toast.LENGTH_LONG).show()
                }

                articlesAdapter.notifyDataSetChanged()
            }
        }

        asyncTask.execute(null, null, null)


    }
}
