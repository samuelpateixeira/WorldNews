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

class ArticlesAdapter(context: Context, objects : ArrayList<Article>) : ArrayAdapter<Article>(context, R.layout.article_list_item, objects) {

    var imageLoaded : Array<Boolean> = Array(22) {false}

    // code to get each view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // inflate a new view with article_list_item.xml
        var articleView = LayoutInflater.from(context).inflate(
            R.layout.article_list_item, parent, false)

        //region old code (commented)
        //var articleView = convertView //get the view for reuse

        // Check if the existing view is being reused, otherwise inflate the view
        //if (articleView == null) {
        //    articleView = LayoutInflater.from(context).inflate(
        //        R.layout.article_list_item, parent, false)
        //}
        //endregion

        // Get the views inside the articleView
        val tvTitle = articleView!!.findViewById<TextView>(R.id.tv_title)
        val tvDate = articleView.findViewById<TextView>(R.id.tv_date)
        val imageView = articleView.findViewById<ImageView>(R.id.imgView)

        //region old code (commented)
        //imageView.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background) )
        //endregion

        // Get the Article object located at this position in the ArrayList
        var currentArticle = getItem(position)

        // Populate the views
        tvTitle.text = currentArticle!!.title
        tvDate.text = currentArticle!!.publishedAt

        // create intent to open the article in another activity
        val intent = Intent(context, ArticleDetailActivity::class.java)
        // add the title and url to the intent
        intent.putExtra("title",currentArticle.title)
        intent.putExtra("url",currentArticle.url)
        intent.putExtra("source", currentArticle.source)

        // set a click listener to start the intent
        articleView.setOnClickListener {
            context.startActivity(intent)
        }


        // load images asynchronously
        val loadImagesAsync = object : AsyncTask<Void, Void, Bitmap>(){

            override fun doInBackground(vararg params: Void?): Bitmap? {

                Log.i("loadImagesAsync", "loading image \n for: " + currentArticle.title)

                // try to load the image
                try {
                    // if there is a URL to image
                    if (currentArticle.urlToImage != null){

                        //if (!imageLoaded[position]) { // if image hasn't been loaded
                            // get url to image
                            val url = URL(currentArticle.urlToImage)
                            // put the image bitmap in the article object
                            currentArticle.image = BitmapFactory.decodeStream(url.openConnection().getInputStream()) // or //val input = url.openStream() //val bmp = BitmapFactory.decodeStream(input) //return bmp
                            imageLoaded[position] = true    // image is loaded
                        //}

                    }
                }

                //region catch exceptions and log them
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
                //endregion

                //return the image
                return currentArticle.image

            }

            override fun onPostExecute(bmpImage: Bitmap?) {
                super.onPostExecute(bmpImage)

                // if there is an image
                if (bmpImage != null) {

                    // show the imageView
                    imageView.visibility = View.VISIBLE
                    // set the image to the imageView
                    imageView.setImageBitmap(bmpImage)

                // if there is no image
                } else {

                    // hide the imageView
                    imageView.visibility = View.GONE

                }
            }

        }.execute()

        //return the imageView
        return articleView
    }


}
