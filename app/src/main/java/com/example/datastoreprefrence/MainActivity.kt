package com.example.datastoreprefrence

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.datastoreprefrence.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var TAG:String="MainActivity"
    val nameKey = stringPreferencesKey("name")
    val emailKey = stringPreferencesKey("email")
    val phoneKey = stringPreferencesKey("phone")
    private val settingsDataStore by preferencesDataStore(name = "app_settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListener()
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            settingsDataStore.data.map {
                Log.e(TAG, "clickListener => name = " + it[nameKey])
                Toast.makeText(this@MainActivity, it[nameKey], Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clickListener() {
        lateinit var name: String
        lateinit var email: String
        lateinit var phone: String
        binding.button.setOnClickListener {
            name = binding.etName.text.toString();
            email = binding.etEmail.text.toString();
            phone = binding.etPhone.text.toString();

            lifecycleScope.launch {
                settingsDataStore.edit { pref ->
                    pref[nameKey] = name
                    pref[emailKey] = email
                    pref[phoneKey] = phone
                }


            }
        }
    }
}