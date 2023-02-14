package com.example.seekbar

import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var celsiusSeekBar: SeekBar
    private lateinit var fahrenheitSeekBar: SeekBar
    private lateinit var temperatureDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        celsiusSeekBar = findViewById(R.id.celsius_seekbar)
        fahrenheitSeekBar = findViewById(R.id.fahrenheit_seekbar)
        temperatureDisplay = findViewById(R.id.temperature_display)

        // set up event listeners for the SeekBars
        celsiusSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // update the Fahrenheit SeekBar when Celsius SeekBar is moved
                val fahrenheitValue = celsiusToFahrenheit(progress)
                fahrenheitSeekBar.progress = fahrenheitValue
                updateTemperatureDisplay(progress, fahrenheitValue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        fahrenheitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // update the Celsius SeekBar when Fahrenheit SeekBar is moved
                val celsiusValue = fahrenheitToCelsius(progress)
                celsiusSeekBar.progress = celsiusValue
                updateTemperatureDisplay(celsiusValue, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    // convert Celsius to Fahrenheit
    private fun celsiusToFahrenheit(celsius: Int): Int {
        return ((celsius * 9) / 5) + 32
    }

    // convert Fahrenheit to Celsius
    private fun fahrenheitToCelsius(fahrenheit: Int): Int {
        return ((fahrenheit - 32) * 5) / 9
    }

    // update the temperature display and show the Snackbar if needed
    private fun updateTemperatureDisplay(celsius: Int, fahrenheit: Int) {
        val displayText = "$celsius °C / $fahrenheit °F"
        temperatureDisplay.text = displayText

        val isCold = celsius <= 20
        val snackbarText = if (isCold) "I wish it were warmer." else "I wish it were colder."
        val snackbar = Snackbar.make(findViewById(android.R.id.content), snackbarText, Snackbar.LENGTH_SHORT)
        if (isCold) {
            snackbar.setBackgroundTint(Color.BLUE)
        } else {
            snackbar.setBackgroundTint(Color.RED)
        }
        snackbar.show()
    }
}



