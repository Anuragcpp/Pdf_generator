package com.example.pdf_generator

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pdf_generator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (checkPermission()) {
            Toast.makeText(this,"Permission Granted!",Toast.LENGTH_SHORT).show()
        }else requestPermission()


    }

    private fun checkPermission() : Boolean {
        //chick permission
        val permission1 = ContextCompat.checkSelfPermission(applicationContext,WRITE_EXTERNAL_STORAGE)
        val permission2 = ContextCompat.checkSelfPermission(applicationContext,READ_EXTERNAL_STORAGE)

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        //request permission if not provided
        ActivityCompat.requestPermissions(
            this,
            arrayOf(WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults.isNotEmpty()) {
                //after requesting permissions we are showing
                // users a toast message of permission granted.
                val writePermission : Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readPermission : Boolean = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (writePermission && readPermission ) Toast.makeText(this,"Permission Granted!",Toast.LENGTH_SHORT).show()
                else Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private var PERMISSION_REQUEST_CODE : Int = 200
    }
}