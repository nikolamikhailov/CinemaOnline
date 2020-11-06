package ru.l4gunner4l.cinemaonline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator by lazy { createNavigator() }
    private val router: Router by inject(named(MOVIES_QUALIFIER))
    private val navHolder: NavigatorHolder by inject(named(MOVIES_QUALIFIER))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_holder)
        if (savedInstanceState == null) router.navigateTo(MoviesScreen())
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            finish()
        } else {
            router.exit()
        }
    }

    override fun onResume() {
        super.onResume()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    private fun createNavigator(): Navigator =
        SupportAppNavigator(
            this,
            supportFragmentManager,
            R.id.fragmentHolder
        )
}