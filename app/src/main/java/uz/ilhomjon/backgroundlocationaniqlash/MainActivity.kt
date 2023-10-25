package uz.ilhomjon.backgroundlocationaniqlash

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.github.florent37.runtimepermission.kotlin.askPermission
import uz.ilhomjon.backgroundlocationaniqlash.databinding.ActivityMainBinding
import uz.ilhomjon.backgroundlocationaniqlash.service.MyLocationService
import uz.ilhomjon.backgroundlocationaniqlash.utils.MyFindLocation
import uz.ilhomjon.backgroundlocationaniqlash.utils.MySharedPreference

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MySharedPreference.init(this)
//        MyFindLocation.locationLiveData.observe(this){
//            binding.tvLocation.text = it.toString()
//        }
        binding.tvLocation.setOnClickListener{
            binding.tvLocation.text = MySharedPreference.locationList.toString()
        }
        binding.tvLocation.setOnLongClickListener {
            MySharedPreference.locationList = ArrayList()
            binding.tvLocation.text = MySharedPreference.locationList.toString()
            true
        }
        binding.btnMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.FOREGROUND_SERVICE){
            //all permissions already granted or just granted
            ContextCompat.startForegroundService(this, Intent(this, MyLocationService::class.java))
        }.onDeclined { e ->
            if (e.hasDenied()) {

                AlertDialog.Builder(this)
                    .setMessage("Please accept our permissions")
                    .setPositiveButton("yes") { dialog, which ->
                        e.askAgain();
                    } //ask again
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss();
                    }
                    .show();
            }

            if(e.hasForeverDenied()) {
                //the list of forever denied permissions, user has check 'never ask again'

                // you need to open setting manually if you really need it
                e.goToSettings();
            }
        }

    }
}