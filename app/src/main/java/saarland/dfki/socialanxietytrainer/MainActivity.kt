package saarland.dfki.socialanxietytrainer

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import kotlinx.android.synthetic.main.content_main.*
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.support.v4.app.NotificationCompat
import android.widget.Toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //----
        val isFirstRun:Boolean = getSharedPreferences ("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true)
        if (isFirstRun) {
            //Start Tutorial
            showTutorial()

            }
        //----
        setSupportActionBar(toolbar)

        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Social Anxiety Trainer")
                .setContentText("Welcome to the Social Anxiety Trainer! We are looking forward" +
                        "to assist you")
        val notification = builder.build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)

        button_execute_task.setOnClickListener { view ->
            val intent = Intent(view.context, ExecuteTaskActivity::class.java)
            startActivity(intent)
        }
        button_progress.setOnClickListener { view ->
            val intent = Intent(view.context, ExecuteTaskActivity::class.java)
            startActivity(intent)
            /*TODO*/
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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

            }
            R.id.nav_settings -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    fun showTutorial()
    {
        val builder1 = AlertDialog.Builder(this)

      //  new AlertDialog.Builder(this)
        builder1.setTitle("Welcome to the tutorial")
        builder1.setMessage("Don't worry, you will see this only once...")
      /*  builder1.setPositiveButton("OK", new DealogInterface . OnClickListener ()
                {
                    @override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss()
                    }

                })*/
        builder1.show()
        }
}


