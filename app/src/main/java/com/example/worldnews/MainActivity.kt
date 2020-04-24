package com.example.worldnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    var newsIntent = Intent(this, News::class.java)
    var countryCode = "PT"


    inner class OnCountrySpinnerSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var text = parent!!.getItemAtPosition(position).toString()

            when (text) {
                "Portugal" -> countryCode = "pt"
                "Itália" -> countryCode = "it"
                "Brazil" -> countryCode = "br"
                "Reino Unido" -> countryCode = "gb"
                "França" -> countryCode = "fr"
            }

            //Toast.makeText(parent.context, text, Toast.LENGTH_SHORT).show()
            newsIntent.putExtra("country","country=" + countryCode + "&")
            newsIntent.putExtra("countryPT", text)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }
    inner class OnCategorySpinnerSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var text = parent!!.getItemAtPosition(position).toString()

            var categoryCode = "geral"

            when (text) {
                "negócios" -> categoryCode = "business"
                "entertenimento" -> categoryCode = "entertainment"
                "geral" -> categoryCode = "general"
                "saúde" -> categoryCode = "health"
                "ciência" -> categoryCode = "science"
                "desporto" -> categoryCode = "sports"
                "tecnologia" -> categoryCode = "technology"
            }

            //Toast.makeText(parent.context, text, Toast.LENGTH_SHORT).show()
            newsIntent.putExtra("category","category=" + categoryCode + "&")
            newsIntent.putExtra("categoryPT",text)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    //https://newsapi.org/v2/top-headlines?country=pt&apiKey=63faeb43aeff400a8cad6e075c433300

    val articles : MutableList<Article> = ArrayList<Article>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsIntent = Intent(this , News::class.java)


        val spinnerAdapterCountries = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item)
        spinnerAdapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_country.adapter = spinnerAdapterCountries
        spinner_country.onItemSelectedListener = OnCountrySpinnerSelectedListener()

        val spinnerAdapterCategories = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item)
        spinnerAdapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_category.adapter = spinnerAdapterCategories
        spinner_category.onItemSelectedListener = OnCategorySpinnerSelectedListener()



    btn_check_news.setOnClickListener(){
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        startActivity(newsIntent)
        }

    }

    companion object {
        const val BASE_API = "https://newsapi.org/v2/"
        const val PATH = "top-headlines?"
        const val API_KEY = "apiKey=63faeb43aeff400a8cad6e075c433300"
    }



}
