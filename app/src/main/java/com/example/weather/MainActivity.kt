package com.example.weather

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import java.io.IOException

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var loc: TextView
    lateinit var submit: Button
    lateinit var clear: ImageView
    lateinit var zip: EditText
    lateinit var number: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Weather"
        loc = findViewById(R.id.loc)
        zip = findViewById(R.id.zip)
        clear = findViewById(R.id.clear)
        clear.setOnClickListener {
            zip.text = null
        }
        submit = findViewById(R.id.submit)
        submit.setOnClickListener {
            number = zip.text.toString()
            WebScratch().execute()
        }
    }

    inner class WebScratch : AsyncTask<Void, Void, Void>() {
        private lateinit var words: String
        override fun doInBackground(vararg params: Void?): Void? {
            val url = "https://google.com/search?q=weather+"
            val search = url + number

            try {
                val doc = Jsoup.connect(search).get()
                val location: String = (doc.select("#wob_loc").text())
                val temp: String = (doc.select("#wob_tm").text())
                val precip: String = (doc.select("#wob_dcp").text())
                val humidity: String = (doc.select("#wob_hm").text())
                val wind: String = (doc.select("#wob_ws").text())
                words = location + "\n" + precip + "\nTemperature - " + temp + "° F\n" + "Humidity - " + humidity + "\n Wind - " + wind
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            loc.text = words
        }
    }
}