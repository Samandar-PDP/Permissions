package com.example.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener

class SecondActivity : AppCompatActivity(), PermissionListener {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        button = findViewById(R.id.button)

        Dexter.withContext(this)
            .withPermission(Manifest.permission.CALL_PHONE)
            .withListener(this)
            .check()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        alertDialog()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1?.continuePermissionRequest()
    }
    private fun alertDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Permission")
            setMessage("You should allow permission!")
            setPositiveButton("Ok") { di, _ ->
                ActivityCompat.requestPermissions(this@SecondActivity, arrayOf(Manifest.permission.CALL_PHONE), 0)
                di.dismiss()
            }
        }.create().show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "You should allow permission!", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(intent)
        }
    }
}