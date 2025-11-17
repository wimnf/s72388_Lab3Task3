package com.example.s72388_lab3task3;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import com.example.s72388_lab3task3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding object (generated from activity_main.xml)
    private lateinit var binding: ActivityMainBinding

    // State variables
    private var currentName: String = "" // [cite: 259]
    private var isGreetingEnabled: Boolean = false // [cite: 261]
    private var isShoutMode: Boolean = false // New state variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using Data Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // [cite: 266]

        setupListeners()
    }

    private fun setupListeners() {
        // 1. Text change listener on EditText (TextWatcher) [cite: 269]
        binding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used now, but required by interface [cite: 273]
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentName = s?.toString()?.trim() ?: "" // [cite: 276]
                updateGreeting() // [cite: 277]
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used now [cite: 280]
            }
        })

        // 2. Checked change listener on Switch [cite: 283]
        binding.switchEnable.setOnCheckedChangeListener { _, isChecked ->
            isGreetingEnabled = isChecked // [cite: 285]
            updateStatusText() // [cite: 286]
            updateGreeting() // [cite: 287]
        }

        // Exercise 1b: Checked change listener on CheckBox
        binding.checkShout.setOnCheckedChangeListener { _, isChecked ->
            isShoutMode = isChecked
            updateGreeting() // Recalculate greeting based on shout mode
        }

        // Exercise 2b: SeekBar change listener
        binding.seekTextSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            // This function runs every time the user moves the SeekBar.
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                // Read the current SeekBar value (progress)
                val computedSize: Float

                // If the progress value is less than 12, set the text size to 12sp (minimum size).
                if (progress < 12) {
                    computedSize = 12f
                } else {
                    // Otherwise, convert the progress value into a float and use it as the text size.
                    computedSize = progress.toFloat()
                }

                // Apply the computed size to the greeting TextView.
                binding.txtGreeting.textSize = computedSize
            }

            // Required, but left empty as no action is needed when drag starts/stops
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            // Required, but left empty as no action is needed when drag starts/stops
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateStatusText() {
        if (isGreetingEnabled) {
            binding.txtStatus.text = "Greeting is enabled." // [cite: 292]
        } else {
            binding.txtStatus.text = "Greeting is disabled." // [cite: 294]
        }
    }

    private fun updateGreeting() {
        var greetingText = ""
        if (isGreetingEnabled) { // [cite: 298]
            if (currentName.isNotEmpty()) {
                // Apply the shout mode here
                greetingText = "Hello, $currentName!"
                if (isShoutMode) {
                    greetingText = greetingText.uppercase() // Exercise 1b: Set to uppercase
                }
            } else {
                greetingText = "Please type your name." // [cite: 301]
            }
        } else {
            // If disabled, greeting is empty [cite: 303, 305]
            greetingText = ""
        }

        binding.txtGreeting.text = greetingText
    }
}