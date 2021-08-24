package com.jahanavi.practical.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.jahanavi.practical.R
import com.jahanavi.practical.databinding.AdapterSearchDataBinding
import com.jahanavi.practical.remote.SearchDetailResponse
import com.layPay.base.BaseBindingViewHolder
import com.layPay.core.BaseBindingAdapter


@Suppress("UNCHECKED_CAST")
class SearchAdapter(private val context: Activity) :
    BaseBindingAdapter<String>(), View.OnClickListener {

    private var countDownTimer // built in android class
            : CountDownTimer? = null

    // CountDownTimer
    private var totalTimeCountInMilliseconds // total count down time in
            : Long = 0

    // milliseconds
    private var timeBlinkInMilliseconds // start time of start blinking
            : Long = 0
    private var blink = false

    var binding : AdapterSearchDataBinding?= null

    override fun bind(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding {
        return AdapterSearchDataBinding.inflate(inflater, parent, false)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
    binding   = holder.binding as AdapterSearchDataBinding
        var data = items!![position]

        if(data.length > 0)

        binding!!.btnStartTime.setOnClickListener(this)
        binding!!.btnStopTime.setOnClickListener(this)


    }



    override fun onClick(v: View?) {
        if (v!!.id == R.id.btnStartTime) {
            binding!!.tvTimeCount.setTextAppearance(
                context,
                R.style.normalText
            )
            setTimer()
            binding!!.btnStartTime.setVisibility(View.VISIBLE)
            binding!!.btnStopTime.setVisibility(View.GONE)
            binding!!.edtTimerValue.setVisibility(View.GONE)
            binding!!.edtTimerValue.setText("")
            startTimer()
        } else if (v.id == R.id.btnStopTime) {
            countDownTimer!!.cancel()
            binding!!.btnStartTime.setVisibility(View.VISIBLE)
            binding!!.btnStopTime.setVisibility(View.GONE)
            binding!!.edtTimerValue.setVisibility(View.VISIBLE)
        }


    }

    override fun getItemCount(): Int {
        return items!!.size
    }


    private fun setTimer() {
        var time = 0
        if (binding!!.edtTimerValue.getText().toString() != "") {
            time =  binding!!.edtTimerValue.getText().toString().toInt()
        } else Toast.makeText(
            this.context, "Please Enter Minutes...",
            Toast.LENGTH_LONG
        ).show()
        totalTimeCountInMilliseconds = (60 * time * 1000).toLong()
        timeBlinkInMilliseconds = (30 * 1000).toLong()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds
            @SuppressLint("SetTextI18n")
            override fun onTick(leftTimeInMilliseconds: Long) {
                val seconds = leftTimeInMilliseconds / 1000
                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    binding!!.tvTimeCount.setTextAppearance(
                        context,
                        R.style.blinkText
                    )
                    // change the style of the textview .. giving a red
                    // alert style
                    if (blink) {
                        binding!!.tvTimeCount.setVisibility(View.VISIBLE)
                        // if blink is true, textview will be visible
                    } else {
                        binding!!.tvTimeCount.setVisibility(View.INVISIBLE)
                    }
                    blink = !blink // toggle the value of blink
                }
                binding!!.tvTimeCount.setText(
                    String.format(
                        "%02d",
                        seconds / 60
                    ) + ":" + String.format("%02d", seconds % 60)
                )
                // format the textview to show the easily readable format
            }

            override fun onFinish() {
                // this function will be called when the timecount is finished
                binding!!.tvTimeCount.setText("Time up!")
                binding!!.tvTimeCount.setVisibility(View.VISIBLE)
                binding!!.btnStartTime.setVisibility(View.VISIBLE)
                binding!!.btnStopTime.setVisibility(View.GONE)
                binding!!.edtTimerValue.setVisibility(View.VISIBLE)
            }
        }.start()
    }

}


