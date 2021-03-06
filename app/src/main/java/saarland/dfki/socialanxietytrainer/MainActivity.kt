package saarland.dfki.socialanxietytrainer

import android.app.AlarmManager
import android.Manifest
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.support.constraint.ConstraintLayout
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import saarland.dfki.socialanxietytrainer.classification.ClassificationManager
import saarland.dfki.socialanxietytrainer.db.DbHelper
import saarland.dfki.socialanxietytrainer.task.SetupAsyncTask
import saarland.dfki.socialanxietytrainer.task.TaskManager
import saarland.dfki.socialanxietytrainer.heartrate.HeartRateSimulator
import android.app.PendingIntent
import android.content.Context
import java.util.*
import saarland.dfki.socialanxietytrainer.reminder.AlarmNotificationReceiver
import saarland.dfki.socialanxietytrainer.reminder.NotificationService


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val taskSetup : SetupAsyncTask = SetupAsyncTask(this)
    private var taskManager: TaskManager? = null

    private var constraintLayout: ConstraintLayout? = null


    private val REQUEST_PERMISSIONS = 108
    private val permissions = arrayOf(Manifest.permission.BODY_SENSORS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    companion object {
        val simulator = HeartRateSimulator()
        val classificationManager = ClassificationManager()
        var dbHelper: DbHelper? = null
        var bandConnectAcitivity: BandConnectAcitivity? = null
        var context: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //creates the list of tasks. the logic of the former button execute_task is in the class TaskAdapter
        taskSetup.execute()
        context = applicationContext;
        constraintLayout = findViewById(R.id.layout)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)



        dbHelper = DbHelper.getInstace(this)

        // Setup notification service
        setupNotifications()

        // Permissions setup
        checkPermissions()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        when (item.itemId) {
//            R.id.action_settings -> return true
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    private fun checkGenderSelection() {
        if(Preferences.Companion.getGender(this) == "None") {
            val intent = Intent(this@MainActivity, SelectGenderActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupNotifications() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(this@MainActivity, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, NotificationService.HOUR)
            calendar.set(Calendar.MINUTE, NotificationService.MIN)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent)
        } else {
            val date = Date()
            date.hours = NotificationService.HOUR
            date.minutes = NotificationService.MIN
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                date.time,
                AlarmManager.INTERVAL_DAY,
                pendingIntent)
        }

    }

    fun setTaskManager(taskManager: TaskManager) {
        this.taskManager = taskManager
    }

    fun getTaskManager(): TaskManager? {
        return taskManager
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_tasks -> {


            }
            R.id.nav_progress -> {
                val intent = Intent(applicationContext, StatisticsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_share -> {
                val intent = Intent(applicationContext, ShareActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_score -> {
                val intent = Intent(applicationContext, ScoreActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_settings -> {
                val intent = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_help -> {
                val intent = Intent(applicationContext, Help::class.java)
                startActivity(intent)
            }
            R.id.nav_demo -> {
                val intent = Intent(applicationContext, Demo::class.java)
                startActivity(intent)
            }

            //open Activity to connect Microsoft Band
            R.id.nav_connect -> {
                val intent = Intent(
                        applicationContext,
                        BandConnectAcitivity::class.java
                )
                startActivity(intent)

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (permissions.any {
                        p -> ContextCompat.checkSelfPermission(this, p) !== PackageManager.PERMISSION_GRANTED }) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)
            }
        }
    }

}
