package com.example.worldnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    //create intent to open the News page
    var newsIntent = Intent(this, News::class.java)






    // country spinner item selected behaviour
    inner class OnCountrySpinnerSelectedListener : AdapterView.OnItemSelectedListener {

        // when an item is selected
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var text = parent!!.getItemAtPosition(position).toString()

            // var for country selection
            var countryCode = "PT"

            when (text) {
                "Portugal" -> countryCode = "pt"
                "Itália" -> countryCode = "it"
                "Brazil" -> countryCode = "br"
                "Reino Unido" -> countryCode = "gb"
                "França" -> countryCode = "fr"
            }

            // put extra info in intent
            newsIntent.putExtra("country","country=" + countryCode + "&")
            newsIntent.putExtra("countryPT", text)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    // category spinner item selected behaviour
    inner class OnCategorySpinnerSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var text = parent!!.getItemAtPosition(position).toString()

            //var for category selection
            var categoryCode = "general"

            when (text) {
                "negócios" -> categoryCode = "business"
                "entertenimento" -> categoryCode = "entertainment"
                "geral" -> categoryCode = "general"
                "saúde" -> categoryCode = "health"
                "ciência" -> categoryCode = "science"
                "desporto" -> categoryCode = "sports"
                "tecnologia" -> categoryCode = "technology"
            }

            // put extra info in intent
            newsIntent.putExtra("category","category=" + categoryCode + "&")
            newsIntent.putExtra("categoryPT",text)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    // api example link https://newsapi.org/v2/top-headlines?country=pt&apiKey=63faeb43aeff400a8cad6e075c433300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create intent to open the News page
        newsIntent = Intent(this, News::class.java)

        // create spinner adapter
        val spinnerAdapterCountries = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item)
        // set resource for dropdown item
        spinnerAdapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // set the adapter
        spinner_country.adapter = spinnerAdapterCountries
        // set selection listener
        spinner_country.onItemSelectedListener = OnCountrySpinnerSelectedListener()

        // create spinner for category selection
        val spinnerAdapterCategories = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item)
        // set resource for dropdown item
        spinnerAdapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // set the adapter
        spinner_category.adapter = spinnerAdapterCategories
        // set selection listener
        spinner_category.onItemSelectedListener = OnCategorySpinnerSelectedListener()


        // set "check news" button click listener
        btn_check_news.setOnClickListener(){
            // start the intent to open the news page
            startActivity(newsIntent)
            }

    }

    // important API data
    companion object {
        const val BASE_API = "https://newsapi.org/v2/"
        const val PATH = "top-headlines?"
        const val API_KEY = "apiKey=63faeb43aeff400a8cad6e075c433300"
    }



}
