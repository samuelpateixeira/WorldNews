package com.example.worldnews

import android.graphics.Bitmap
import android.util.Log
import org.json.JSONObject

class Article {

    //region Properties

    var source      : String? = null
    var author      : String? = null
    var title       : String? = null
    var description : String? = null
    var url         : String? = null
    var urlToImage  : String? = null
    var publishedAt : String? = null
    var content     : String? = null
    var image       : Bitmap? = null
    var imageLoaded = false

    //endregion

    //region Constructors

    constructor(
        source      : String?,
        author      : String?,
        title       : String?,
        description : String?,
        url         : String?,
        urlToImage  : String?,
        publishedAt : String?,
        content     : String?
    ) {
        this.source         = source
        this.author         = author
        this.title          = title
        this.description    = description
        this.url            = url
        this.urlToImage     = urlToImage
        this.publishedAt    = publishedAt
        this.content        = content
    }

    constructor(
    ) {
    }
    //endregion

    //region Companion Object
    companion object{

        //get article from JSON article
        fun parseJson(jsonArticle : JSONObject) : Article {
            val article = Article()
            article.author      = jsonArticle.getString("author")
            article.title       = jsonArticle.getString("title")
            article.description = jsonArticle.getString("description")
            article.url         = jsonArticle.getString("url")
            article.urlToImage  = jsonArticle.getString("urlToImage")
            article.publishedAt = jsonArticle.getString("publishedAt")
            article.content     = jsonArticle.getString("content")
            var sourceObject    = jsonArticle.getJSONObject("source")
            article.source      = sourceObject.getString("name")
            Log.e("sourceDebug", "source: " + article.source)

            return article
        }
    }
    //endregion
}