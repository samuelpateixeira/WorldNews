package com.example.worldnews

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.FileNotFoundException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

class ArticlesAdapter(context: Context, objects : ArrayList<Article>) : ArrayAdapter<Article>(context, R.layout.article_list_item, objects ) {

    var imageLoaded : Array<Boolean> = Array(22) {false}
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            var articleView = LayoutInflater.from(context).inflate(
                R.layout.article_list_item, parent, false)


        //var articleView = convertView

        // Check if the existing view is being reused, otherwise inflate the view
        //if (articleView == null) {
        //    articleView = LayoutInflater.from(context).inflate(
        //        R.layout.article_list_item, parent, false)
        //}

        // Get the views
        val tvTitle = articleView!!.findViewById<TextView>(R.id.tv_title)
        val tvDate = articleView.findViewById<TextView>(R.id.tv_date)
        val imageView = articleView.findViewById<ImageView>(R.id.imgView)

        //imageView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background) )

        // Get the Person object located at this position in the list
        var currentArticle = getItem(position)

        // Populate the views
        tvTitle.text = currentArticle!!.title
        tvDate.text = currentArticle!!.publishedAt
        val intent = Intent(context, ArticleDetailActivity::class.java)

        intent.putExtra("title",currentArticle.title)
        intent.putExtra("url",currentArticle.url)

        articleView.setOnClickListener {
            context.startActivity(intent)
        }



            val loadImagesAsync = object : AsyncTask<Void, Void, Bitmap>(){

                override fun doInBackground(vararg params: Void?): Bitmap? {

                    Log.i("loadImagesAsync", "loading image \n for: " + currentArticle.title)

                    try {
                        if (currentArticle.urlToImage != null){ // if there is an image

                            //if (!imageLoaded[position]) { // if image hasn't been loaded
                                // load image
                                val url = URL(currentArticle.urlToImage)
                                currentArticle.image = BitmapFactory.decodeStream(url.openConnection().getInputStream()) // or //val input = url.openStream() //val bmp = BitmapFactory.decodeStream(input) //return bmp
                                imageLoaded[position] = true
                            //}

                        }
                    }
                    catch (e: MalformedURLException) {
                        Log.e("MalformedURLException", currentArticle.title + "\nURL: " + currentArticle.urlToImage)
                        return null
                    }
                    catch (e: FileNotFoundException) {
                        Log.e("FileNotFoundException", currentArticle.title + "\nURL: " + currentArticle.urlToImage)
                        return null
                    }
                    catch (e: Exception) {
                        Log.e("Exception", currentArticle.title + "\nURL: " + currentArticle.urlToImage)
                        return null
                    }

                    return currentArticle.image

                }

                override fun onPostExecute(result: Bitmap?) {
                    super.onPostExecute(result)

                    if (result != null) {
                        imageView.visibility = View.VISIBLE
                        imageView.setImageBitmap(result)
                    } else {
                        imageView.visibility = View.GONE
                    }
                }

            }.execute()

        return articleView
    }


}
