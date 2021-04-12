package ir.awlrhm.reminder

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import java.util.*

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
//        val configuration = resources.configuration
//        configuration.setLayoutDirection(Locale("fa"))
//        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}