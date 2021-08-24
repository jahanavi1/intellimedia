package com.jahanavi.practical

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.*
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity() {
    private var progressDialog: Dialog? = null
    private val iv: ImageView? = null
    var gson: Gson? = null
    lateinit var gsonExpose: Gson
    private val CALLBACK_CAMERA = 101
    private val CALLBACK_GALLERY = 102
//    lateinit var googleLoginHelper: GoogleLoginHelper

    val SUFFIX: String = "/"
    val UNDERSCORE: String = "_"
    var fromAttachment = false

    var VIDEO_URI:String = ""
    val visibleFragment: Fragment?
        get() {
            val fragmentManager = supportFragmentManager
            val fragments = fragmentManager.fragments
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
            return null
        }

    val activity: Activity
        get() = this
    val CUSTOM_REQUEST_CODE = 1002
    internal var mCurrentPhotoPath: String? = ""

    private var allPermissionsListener: MultiplePermissionsListener? = null


    public override fun onStart() {
        super.onStart()
    }


    // Created by Jahanavi

    fun locationPermission(multiplePermissionListener: MultiplePermissionsListener) {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).withListener(multiplePermissionListener).check()
    }



    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                    Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Fabric.with(this, Crashlytics());
//        AppUtils.setActivtiy(this)
//        googleLoginHelper =
//            GoogleLoginHelper(activity)
        gson = Gson()
        gsonExpose = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        // Kaushal
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }



    fun dispatchTakeVideoGalleryIntent() {
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "image/* video/*"
        startActivityForResult(pickIntent, CALLBACK_GALLERY)

    }

    fun dispatchVideoGalleryIntent() {
        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "video/*"
        startActivityForResult(pickIntent, CALLBACK_GALLERY)

    }

//    fun logoutSuccess() {
//        Prefs.getInstance(this).setUserInfoToNull()
//        Prefs.getInstance(this).accessToken = ""
//        LoginManager.getInstance().logOut()
//        if (googleLoginHelper != null)
//            googleLoginHelper.signOut(object : GoogleLoginHelper.onSignOutListener {
//                override fun onSignOut() {
//                }
//            })
////        launchNewFirstActivity(LoginActivity::class.java)
//    }

    fun getDecimal(value: Double): Double {
        val formatter = DecimalFormat("#0.000000")
        return java.lang.Double.parseDouble(formatter.format(value))

    }

    fun buttonDisable(button: View) {
        button.alpha = 0.5f
        button.isEnabled = false
    }

    fun buttonEnable(button: View) {
        button.alpha = 1.0f
        button.isEnabled = true
    }


    fun replaceFragment(fragment: Fragment, name: String?, addtToBackStack: Boolean) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.container, fragment, name)
        if (addtToBackStack)
            ft.addToBackStack(name)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    open fun removeFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =   fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment!!)
        fragmentTransaction.commit()
    }

    fun replaceFragmentWithAnimation(
        fragment: Fragment,
        name: String?,
        addtToBackStack: Boolean,
        sharedElement: View,
        trasitionName: String
    ) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.container, fragment, name).addSharedElement(sharedElement, trasitionName)
        if (addtToBackStack)
            ft.addToBackStack(name)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }



    fun launch(activityTosrat: Class<*>) {
        startActivity(Intent(this, activityTosrat))
    }



    fun launchActivityWithViewAnimation(
        view: View,
        transitionName: String,
        activityTosrat: Class<*>
    ) {
        val intent = Intent(this, activityTosrat)

        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, transitionName)
        (activity).startActivity(intent, options.toBundle())
    }

    fun launchNewFirstActivity(activityTosrat: Class<*>) {
        val intent = Intent(applicationContext, activityTosrat)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }

//    fun fadeAnimation(view: View) {
//        val aniFade: Animation =
//            AnimationUtils.loadAnimation(applicationContext, R.anim.fade)
//        view.startAnimation(aniFade)
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
//        AppUtils.setActivtiy(this)
    }


    fun currentActivity(): String {
        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = am.getRunningTasks(1)
        Log.d("topActivity", "CURRENT Activity ::" + taskInfo[0].topActivity?.className)
        //        ComponentName componentInfo = ;
        return taskInfo[0].topActivity?.className.toString()
    }


    fun editFORDollarSign(editText: EditText) {


        editText.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                editText.removeTextChangedListener(this)
                when {

                    s.toString() == "$" -> {
                        editText.setText("")
                        editText.addTextChangedListener(this)
                    }
                    s.startsWith("$").not() -> {
                        editText.setText("$$s")
                        editText.addTextChangedListener(this)
                    }
                    else -> editText.addTextChangedListener(this)
                }


            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                /*when {

                    editable.isEmpty() -> return
//                    RegExp("\\@\\d+").matches(editable.toString()) -> return
                    editable.toString() == "$" -> editable.clear()
                    editable.startsWith("$").not() -> editText.text = editable.insert(0, AppConst.CURRENY)
                }*/


            }
        })

    }

    // first value remove in edittext
    fun removeFirstDollarSign(s: String): String? {
        return s.trim().removePrefix("$")
    }




    fun deleteDirectory() {
//        var dir =
//            File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/Proquotes"))
        val dir =
            File(
                (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/Proquotes")
            )
        dir.delete()
        if (dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                File(dir, children[i]).delete()
            }
        }

    }


    private val RC_SIGN_IN = 1005
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("TAG", "code == " + requestCode)
        if (requestCode == RC_SIGN_IN) {
//            googleLoginHelper.onActivityResult(requestCode, resultCode, data)
        }

    }

   fun showErrorAboveKeypadSnackBar(view: View?, s: String) {
        try {
//                hideKeyboard()
            val snack = Snackbar.make(view!!, s, Snackbar.LENGTH_LONG)
            val sbview = snack.view
            sbview.setBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    android.R.color.holo_red_dark
                )
            )
            val textView = sbview.findViewById<View>(R.id.snackbar_text) as TextView
            textView.setTextColor(ContextCompat.getColor(activity, android.R.color.white))
            snack.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inline fun <reified T> parseArray(json: String, typeToken: Type): T {

        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }

    fun getCurrentFragment(): Fragment? {
        val currentFragment = supportFragmentManager
            .findFragmentById(R.id.container)
        return currentFragment
    }

    fun adapter(provinceList: MutableList<*>): ArrayAdapter<Any> {

        val adapter =
            ArrayAdapter<Any>(this, android.R.layout.simple_spinner_dropdown_item, provinceList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter

    }

    fun adapter(stringArray: Array<String>): ArrayAdapter<Any> {
        val adapter =
            ArrayAdapter<Any>(this, android.R.layout.simple_spinner_dropdown_item, stringArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    interface RedirectToCall {
        fun callRedirection()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
    fun onBackPress(view: View) {
        onBackPressed()
    }

}