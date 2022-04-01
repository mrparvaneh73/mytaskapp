package com.example.usertaskapp.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.usertaskapp.ui.loginandregister.MainActivity
import com.example.usertaskapp.R
import com.example.usertaskapp.ui.ViewPagerAdapter
import com.example.usertaskapp.databinding.ActivityMain2Binding
import com.example.usertaskapp.data.localdatabase.Repository
import com.example.usertaskapp.data.localdatabase.UserDatabase
import com.example.usertaskapp.data.localdatabase.model.Todo
import com.example.usertaskapp.ui.home.dialog.CustomDialogViewModel
import com.example.usertaskapp.ui.home.dialog.CustomDialogViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import java.io.ByteArrayOutputStream
import java.util.*

class MainActivity2 : AppCompatActivity() {
    private val args by navArgs<MainActivity2Args>()
    var imageByteArray: ByteArray? = null
    private var tabtitle = arrayOf("All", "DOING", "DONE")
    private var date: String = "00"
    private var time: String = "00"
    private lateinit var datepicker: DatePicker
    private lateinit var timepicker: TimePicker
    private lateinit var title: EditText
    private lateinit var done: CheckBox
    private lateinit var doing: CheckBox
    private var isdone = false
    private var isdoing = false
    private lateinit var description: EditText
    private lateinit var save: TextView
    private lateinit var cancel: TextView
    private lateinit var binding: ActivityMain2Binding
    private lateinit var customDialogViewModel: CustomDialogViewModel
//    private val cameraluncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
//        imageByteArray = it.toByteArray()
//
//    }
//    private val selectPictureLuncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
//        val change = this?.contentResolver?.openInputStream(it)?.readBytes()
//        imageByteArray = change
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodelprovider()

        binding.viewpager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = tabtitle[position]
        }.attach()

        binding.fab.setOnClickListener {
            showDialog()
        }
    }

    private fun viewmodelprovider() {
        val application = requireNotNull(this).application

        val dao = UserDatabase.getInstance(application).userDao

        val repository = Repository(dao)

        val factory = CustomDialogViewModelFactory(repository, application)

        customDialogViewModel = ViewModelProvider(this, factory).get(CustomDialogViewModel::class.java)
    }

    @SuppressLint("NewApi")
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog)
        var todo: Todo? = null
        datepicker = dialog.findViewById(R.id.btndatapicker)
        timepicker = dialog.findViewById(R.id.btntimepicker)
        save = dialog.findViewById(R.id.tv_save)
        cancel = dialog.findViewById(R.id.tv_cancel)
        title = dialog.findViewById(R.id.title_et)
        done = dialog.findViewById(R.id.done_checkbox)
        doing = dialog.findViewById(R.id.doing_checkbox)
        description = dialog.findViewById(R.id.description_et)
        Log.d(TAG, "showDialog: " + datepicker.textAlignment.toString())


        save.setOnClickListener {
            isdone = done.isChecked
            isdoing = doing.isChecked
            Log.d(TAG, "isdoneactivity: $isdone")
            date = "${datepicker.year}/${datepicker.month + 1}/${datepicker.dayOfMonth}"
            Log.d(TAG, "date: $date")
            time = "${timepicker.hour} : ${timepicker.minute}"
            todo = Todo(
                id = todo?.id ?: 0,
                title = title.text.toString(),
                description = description.text.toString(),
                time = time,
                date = date,
                userOwnerId = args.userid,
                done = isdone,
                doing = isdoing
            )
            customDialogViewModel.addTodo(todo!!)
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }

    fun id(): Int {
        return args.userid
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.account_button) {
            val builder = AlertDialog.Builder(this)

            builder.setMessage("Do you want to sign out?")
            builder.setPositiveButton("Yes") { dialog, which ->
                dialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            builder.create().show()

        }
        if (item.itemId == R.id.delete_button) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do You Want to Delete All?")

            customDialogViewModel.getAll(args.userid).observe(this) {
                if (it.isEmpty()) {
                    Toast.makeText(this, "NO Task Exist", Toast.LENGTH_SHORT).show()
                } else {
                    builder.setPositiveButton("Yes") { dialog, which ->
                        for (i in it) {
                            customDialogViewModel.delete(i)
                        }
                        dialog.dismiss()

                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.create().show()

                }


            }


        }
        return super.onOptionsItemSelected(item)
    }
    fun Bitmap.toByteArray(): ByteArray {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG, 10, this)
            return toByteArray()
        }
    }


}

