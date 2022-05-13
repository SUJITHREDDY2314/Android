package com.example.a7minuteapp

import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteapp.ExerciseModel.Companion.ExercisesList
import com.example.a7minuteapp.databinding.ActivityExBinding
import com.example.a7minuteapp.databinding.DialogReconfirmationBinding
import java.text.SimpleDateFormat
import java.util.*

class ExActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    lateinit var binding:ActivityExBinding
    private var tts:TextToSpeech?= null
    lateinit var customDialog: Dialog
    var music:MediaPlayer? = null
    var countDownTimer:CountDownTimer ? = null
    var timerValue = 1000L
    var restProgress = 0
    var exerciseList:ArrayList<ExerciseModel>?= null
    private var exerciseAdapter:ExerciseStatusAdapter? = null
    private var currentExercisePos = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        val actionbar = supportActionBar
        tts = TextToSpeech(this,this)
        /*
        val a: Set<String> = HashSet()
        val voice = Voice("en-us-x-sfg#male_2-local", Locale.getDefault(), 400,200,true,a)
        tts!!.voice = voice
        tts!!.setVoice(voice)
         */
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBar.setNavigationOnClickListener {
            customDialogForBackConfirmation()
        }
        binding.llrestview.setOnClickListener{
            music = MediaPlayer.create(applicationContext,R.raw.song2)
            music!!.isLooping = true

            music!!.start()
            startCounting()
            binding.llrestview.isClickable = false
            setupExerciseStatusRecyclerView()
        }
        exerciseList = ExercisesList
    }



    private fun customDialogForBackConfirmation() {
        customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_reconfirmation)
        customDialog.show()
    }

    fun customDialogYes(view: View) {
        finish()
        customDialog.dismiss()
    }
    fun customDialogNo(view: View) {
        customDialog.dismiss()
    }
    private fun setupExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!,this)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Toast.makeText(this,"Language is not available",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"Initialization not done",Toast.LENGTH_LONG).show()
        }
    }
    private fun startCounting() {

        binding.llrestview.visibility = View.VISIBLE
        binding.llrestviewthirtysecond.visibility = View.GONE
        binding.exerciseNameChange.text = exerciseList!![currentExercisePos+1].name
        binding.progressbar.progress = restProgress
        countDownTimer = object : CountDownTimer(timerValue,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressbar.progress= 10- restProgress
                binding.tvtimer.text= (millisUntilFinished/1000).toString()
            }
            override fun onFinish() {
                restProgress = 0
                currentExercisePos++
                exerciseList!![currentExercisePos].isSelected= true
                exerciseAdapter!!.notifyDataSetChanged()
                countDownTimer!!.cancel()
                music!!.setVolume(0.4f,0.4f)
                binding.llrestview.visibility = View.GONE
                binding.llrestviewthirtysecond.visibility = View.VISIBLE
                binding.ivImage.setImageResource(exerciseList!![currentExercisePos].imageId)
                binding.exerciseName.text = exerciseList!![currentExercisePos].name
                tts!!.speak(binding.exerciseName.text.toString(),TextToSpeech.QUEUE_ADD,null,"")
                gotoSecondTimer()
            }
        }.start()
    }
    private fun gotoSecondTimer() {
        binding.progressbar2.progress = restProgress

        countDownTimer = object : CountDownTimer(1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                if(restProgress==2){
                    music!!.setVolume(1f,1f)
                }
                restProgress++
                binding.progressbar2.progress= 30 - restProgress

                binding.tvtimer2.text= (millisUntilFinished/1000).toString()
            }
            override fun onFinish() {
                /*
                when(currentExercisePos) {
                    0 -> binding.exerciseNo1.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    1->  binding.exerciseNo2.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    2 -> binding.exerciseNo3.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    3 -> binding.exerciseNo4.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    4 -> binding.exerciseNo5.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    5 -> binding.exerciseNo6.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    6 -> binding.exerciseNo7.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    7 -> binding.exerciseNo8.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    8 -> binding.exerciseNo9.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                    9 -> binding.exerciseNo10.setBackgroundResource(R.drawable.item_circular_color_accent_background2)
                }

                 */
                exerciseList!![currentExercisePos].isSelected= false
                exerciseList!![currentExercisePos].isCompleted= true
                exerciseAdapter!!.notifyDataSetChanged()
                restProgress= 0
                if(currentExercisePos<exerciseList!!.size-1){
                    startCounting()
                }
                else{
                    Toast.makeText(this@ExActivity,"You have completed 7 mins workout",Toast.LENGTH_LONG).show()
                    binding.llrestviewthirtysecond.visibility= View.GONE
                    binding.thankutextview.visibility= View.VISIBLE
                    music!!.stop()
                }
            }
        }.start()
    }
    override fun onDestroy() {
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(music != null){
            music!!.stop()
        }
        super.onDestroy()
    }
    override fun onPause() {
        if(music != null){
            music!!.pause()
        }
        super.onPause()
    }
    override fun onResume() {
        if(music != null){
            music!!.start()
        }
        super.onResume()
    }

    fun goToMainActivity(view: View) {
        binding.FinishButtonUnclickMe.isClickable = false
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf = SimpleDateFormat("dd MM yyyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbObj = SqlDatabase(this,null)
        dbObj.addDate(date)
        Toast.makeText(this,"Date $date",Toast.LENGTH_LONG).show()
        onBackPressed()
    }
}