package ir.awlrhm.reminder

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

fun AppCompatActivity.replaceFragmentInActivity(container: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.commit {
   /*     setCustomAnimations(
            R.anim.enter_anim,
            R.anim.exit_anim
        )*/
        replace(container, fragment, tag)
            .addToBackStack(tag)
    }
}


