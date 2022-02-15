package one.example.androidtaskseeratabbas_androiddeveloper.activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.logomaker.MySharePreferences.MySharePreference
import one.example.androidtaskseeratabbas_androiddeveloper.R
import one.example.androidtaskseeratabbas_androiddeveloper.constants.Constants
import one.example.androidtaskseeratabbas_androiddeveloper.databinding.ActivityHomeScreenBinding
import java.util.*


class HomeScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    private var myPrefHelper: MySharePreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPrefHelper = MySharePreference.getInstance(this)
        setLanguageSelection(myPrefHelper!!.isEnglish)

        val bundle = intent.extras
        bundle?.let {
            if (it.getBoolean(Constants.SHOW_ALERT)) {
                with(binding) {
                    if(myPrefHelper!!.isEnglish){
                        this.tvMessage.text = it.getString(Constants.ALERT_MESSAGE_EN)
                    }else{
                        this.tvMessage.text = it.getString(Constants.ALERT_MESSAGE_AR)

                    }
                    this.layoutMessage.visibility = View.VISIBLE
                    this.tvHomeActivity.visibility = View.GONE
                }
            } else {
                with(binding) {
                    this.layoutMessage.visibility = View.GONE
                    this.tvHomeActivity.visibility = View.VISIBLE
                }
            }
        }

        binding.tvLanguageSelection.setOnClickListener {
            myPrefHelper!!.isEnglish = !myPrefHelper!!.isEnglish
            setLanguageSelection(myPrefHelper!!.isEnglish)
            Toast.makeText(this@HomeScreenActivity, getString(R.string.language_change), Toast.LENGTH_SHORT).show()
            recreate()
        }
    }

    private fun setLanguageSelection(isEnglish: Boolean) {
        var locale: Locale? = null
        if (isEnglish) {
            locale = Locale("en")
            binding.tvLanguageSelection.text = Constants.LANGUAGE_ARABIC
        } else {
            locale = Locale("ar")
            binding.tvLanguageSelection.text = Constants.LANGUAGE_ENGLISH
        }
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
    }
}