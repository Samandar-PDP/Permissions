package com.example.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.btn)

        checkPermission()

    }

    private fun checkPermission() {
        val list = mutableListOf<String>()
        if (!callPermission()) {
            list.add(Manifest.permission.CALL_PHONE)
        }
        if (!calendarPermission()) {
            list.add(Manifest.permission.READ_CALENDAR)
        }
        if (!readContactPermission()) {
            list.add(Manifest.permission.READ_CONTACTS)
        }
        if (list.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, list.toTypedArray(), 100)
        }
    }

    private fun callPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    private fun calendarPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED

    private fun readContactPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestCallPhonePermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CALL_PHONE
            )
        ) {
            AlertDialog.Builder(this).apply {
                setTitle("Permission")
                setMessage("You should allow permission!")
                setPositiveButton("Ok") { di, _ ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        100
                    )
                    di.dismiss()
                }
            }.create().show()
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.CALL_PHONE),
                100
            )
        }
    }

    private fun isCallPhonePermissionAvailable() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("@@@","${grantResults[i]}")
                }
            }
        }
    }

    private fun callPhone() {
        val phoneNumber = Uri.parse("tel:+998901739003")
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = phoneNumber
        startActivity(intent)
    }
}