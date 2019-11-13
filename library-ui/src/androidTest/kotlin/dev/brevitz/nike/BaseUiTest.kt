package dev.brevitz.nike

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {
    private val app = ApplicationProvider.getApplicationContext<App>()
    private val okHttpResource = OkHttpIdlingResource(app.coreComponent().okHttp())

    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val ruleChain: RuleChain = RuleChain.outerRule(activityRule)

    open fun setup() {
        IdlingRegistry.getInstance().register(okHttpResource)

        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create(COMPUTATION_IDLER_NAME))
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create(IO_IDLER_NAME))
    }

    fun clearIdlingResources() {
        IdlingRegistry.getInstance().unregister(okHttpResource)

        RxJavaPlugins.setInitComputationSchedulerHandler { Schedulers.computation() }
        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.io() }
    }

    private companion object {
        private const val COMPUTATION_IDLER_NAME = "Computation"
        private const val IO_IDLER_NAME = "IO"
    }
}
