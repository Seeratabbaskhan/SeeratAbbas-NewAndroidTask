package one.example.androidtaskseeratabbas_androiddeveloper.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.logomaker.MySharePreferences.MySharePreference
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import one.example.androidtaskseeratabbas_androiddeveloper.BuildConfig
import one.example.androidtaskseeratabbas_androiddeveloper.R
import one.example.androidtaskseeratabbas_androiddeveloper.constants.Constants
import one.example.androidtaskseeratabbas_androiddeveloper.databinding.ActivitySplashBinding
import java.util.*


class SplashScreenActivity : AppCompatActivity() {
    var runnable: Runnable? = null
    var handler: Handler? = null
    var remoteConfig: FirebaseRemoteConfig? = null
    var alert_message_en = ""
    var alert_message_ar = ""
    var show_alert = false
    lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguageSelection(MySharePreference.getInstance(this)!!.isEnglish)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            Intent(this@SplashScreenActivity, HomeScreenActivity::class.java).apply {
                this.putExtra(Constants.SHOW_ALERT, show_alert)
                this.putExtra(Constants.ALERT_MESSAGE_AR, alert_message_ar)
                this.putExtra(Constants.ALERT_MESSAGE_EN, alert_message_en)
                startActivity(this)
                finish()
            }

        }

        getFirebaseRemoteConfigData()
    }

    private fun getFirebaseRemoteConfigData() {
        try {
            remoteConfig = FirebaseRemoteConfig.getInstance()
            remoteConfig?.let {
                if (BuildConfig.DEBUG) {
                    it.setConfigSettingsAsync(
                        FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(0)
                            .build()
                    )
                }
                it.setDefaultsAsync(R.xml.remote_config_defaults)
                it.fetchAndActivate()
                    .addOnCompleteListener { task: Task<Boolean?>? ->
                        alert_message_ar = it.getString(Constants.ALERT_MESSAGE_AR)
                        alert_message_en = it.getString(Constants.ALERT_MESSAGE_EN)
                        show_alert = it.getBoolean(Constants.SHOW_ALERT)
                    }
            }

        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }


    private fun startHandler() {
        if (handler != null && runnable != null) {
            handler!!.postDelayed(runnable!!, 3000)
        }
    }

    private fun stopHandler() {
        if (handler != null && runnable != null) {
            handler!!.removeCallbacks(runnable!!)
        }
    }

    override fun onResume() {
        super.onResume()
        startHandler()
    }

    override fun onPause() {
        super.onPause()
        stopHandler()
    }


    private fun setLanguageSelection(isEnglish : Boolean){
        var locale : Locale? = null
        if (isEnglish){
            locale   = Locale("en")
        }else{
            locale   = Locale("ar")
        }
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
    }

}