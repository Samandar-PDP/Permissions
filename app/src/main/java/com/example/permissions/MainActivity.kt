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
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         button = findViewById(R.id.btn)

        if (isCallPhonePermissionAvailable()) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            button.isVisible = true
        } else {
            checkPermission()
        }
        button.setOnClickListener {
            callPhone()
        }
    }

    // 1

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

//    private fun requestCallPhonePermission2() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                Manifest.permission.CALL_PHONE
//            )
//        ) {
//            AlertDialog.Builder(this).apply {
//                setTitle("Permission")
//                setMessage("You should allow permission!")
//                setPositiveButton("Ok") { di, _ ->
//                    requestPer()
//                    di.dismiss()
//                }
//            }.create().show()
//        } else {
//            requestPer()
//        }
//    }
//
    private fun isCallPhonePermissionAvailable() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 100) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
//                    button.isVisible = true
//            } else {
//                requestPer()
//            }
//        }
//    }
//
//    private fun requestPer() {
//        ActivityCompat.requestPermissions(
//            this@MainActivity,
//            arrayOf(Manifest.permission.CALL_PHONE),
//            100
//        )
//    }

    private fun callPhone() {
        val phoneNumber = Uri.parse("tel:+998901739003")
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = phoneNumber
        startActivity(intent)
    }
}