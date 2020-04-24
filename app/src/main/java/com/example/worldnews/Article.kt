package com.example.worldnews

import android.graphics.Bitmap
import org.json.JSONObject

class Article {


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

    companion object{
        fun parseJson(jsonArticle : JSONObject) : Article {
            val article = Article()
            article.author      = jsonArticle.getString("author")
            article.title       = jsonArticle.getString("title")
            article.description = jsonArticle.getString("description")
            article.url         = jsonArticle.getString("url")
            article.urlToImage  = jsonArticle.getString("urlToImage")
            article.publishedAt = jsonArticle.getString("publishedAt")
            article.content     = jsonArticle.getString("content")

            return article
        }
    }

}