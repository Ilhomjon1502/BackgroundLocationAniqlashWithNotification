package uz.ilhomjon.backgroundlocationaniqlash.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import uz.ilhomjon.backgroundlocationaniqlash.utils.MyData
import uz.ilhomjon.backgroundlocationaniqlash.utils.MyFindLocation

class MyLocationService : Service() {

    lateinit var myFindLocation: MyFindLocation
    override fun onCreate() {
        super.onCreate()
        myFindLocation = MyFindLocation(applicationContext)
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        myFindLocation.checkSettingsAndStartUpdates()
        startForeground(1, MyData.createNotification(applicationContext, "Taxi narxi: ","sanoq"))

        return START_STICKY
    }
}