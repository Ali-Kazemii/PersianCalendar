package ir.awlrhm.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gotoMainFragment()
    }

    private fun gotoMainFragment() {
        replaceFragmentInActivity(
            R.id.container,
            MainFragment {
                gotoAddReminder()
            },
            MainFragment.TAG
        )
    }

    private fun gotoAddReminder() {
        replaceFragmentInActivity(
            R.id.container,
            AddReminderFragment(),
            AddReminderFragment.TAG
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            this.finish()
    }
}