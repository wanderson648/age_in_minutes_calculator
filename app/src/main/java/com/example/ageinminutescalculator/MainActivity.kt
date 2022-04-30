package com.example.ageinminutescalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button =  findViewById(R.id.btn_date_picker)

        tvSelectedDate = findViewById(R.id.tv_selected_date)
        tvAgeInMinutes = findViewById(R.id.tv_age_minutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear"
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            tvSelectedDate?.text = selectedDate

            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                val  selectedDateInMinutes = theDate.time / 60000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000

                    val differenceInMinutes  = currentDateInMinutes - selectedDateInMinutes

                    tvAgeInMinutes?.text = differenceInMinutes.toString()
                }
            }
        },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}