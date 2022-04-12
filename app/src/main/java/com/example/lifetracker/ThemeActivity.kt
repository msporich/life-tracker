package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.example.lifetracker.databinding.ActivitySettingsBinding
import com.example.lifetracker.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {
    private var xyz: Switch? = null
    private lateinit var saveData: SaveData
    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        xyz = findViewById<View>(R.id.changeThemeSwitch) as Switch?

        if (saveData.loadDarkModeState() == true) {
            xyz!!.isChecked = true
        }

        xyz!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.modeText.text = "Dark Mode"
                setTheme(R.style.Theme_LifeTrackerNight)
                saveData.setDarkModeState(true)
                restartApplication()
            }
            else{
                binding.modeText.text = "Light Mode"
                setTheme(R.style.Theme_LifeTracker)
                saveData.setDarkModeState(false)
                restartApplication()
            }
        }


    }

    private fun restartApplication(){
        finish()
        var intent = Intent(this, ThemeActivity::class.java)
        startActivity(intent)
    }
}
