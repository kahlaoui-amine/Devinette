package com.example.Devinette

import java.util.*
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var textView : TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        var isRunning :Boolean=false
        var countDownTimer: CountDownTimer? = null
        val builder = AlertDialog.Builder(this)
        val dialog =Dialog(this)
        val input = EditText(this)
        val NumberEntered = findViewById<EditText>(R.id.ev_num)
        val tv_historique = findViewById<TextView>(R.id.historique)
        val looser =findViewById<TextView>(R.id.tv_looser)
        val result = findViewById<TextView>(R.id.result)
        val SVhis=findViewById<ScrollView>(R.id.SVhis)
        val his=findViewById<TextView>(R.id.his)
        val topScore=findViewById<TextView>(R.id.topScore)
        val start = findViewById<Button>(R.id.start)
        val Timer = findViewById<TextView>(R.id.timer);
        val Chrono = findViewById<ImageView>(R.id.chrono);
        val intentScore = findViewById<Button>(R.id.intentScore)
        val check = findViewById<Button>(R.id.check)
        val RadioGroup =findViewById<RadioGroup>(R.id.group)
        val CsNiveau =findViewById<TextView>(R.id.CsNiveau)

        var helper=MyDBHelper(applicationContext)
        SVhis.visibility = View.INVISIBLE
        his.visibility = View.INVISIBLE
        val radioGroup:RadioGroup=findViewById(R.id.group)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.expert) {
                SVhis.visibility = View.INVISIBLE
                his.visibility = View.INVISIBLE
                start.visibility =View.VISIBLE

            }
            if (checkedId == R.id.deb) {
                SVhis.visibility = View.VISIBLE
                his.visibility = View.VISIBLE
                start.visibility =View.VISIBLE
                intentScore.visibility =View.VISIBLE
            }
        }
        var db=helper.readableDatabase
        fun  afficheScore() {
            var rs = db.rawQuery("SELECT * FROM USERS ORDER BY SCORE DESC", null)
            var ch: String = ""
            for (i in 0..9) {
                if (rs.moveToNext()) {
                    ch += rs.getString(1) + " : " + rs.getString(2) + "\n"
                }
            }
            topScore.setText(ch)
        }
        afficheScore()

        isRunning=true;

        NumberEntered.visibility = View.INVISIBLE
        check.visibility = View.INVISIBLE
        Timer.visibility = View.INVISIBLE
        Chrono.visibility=View.INVISIBLE
        start.visibility =View.INVISIBLE
        result.visibility =View.INVISIBLE
        var Rnum = 0
        var nbEssai = 0
        var s = "";
        var x = 0;
        var score = 100;
        start.setOnClickListener {
            if (isRunning){
                countDownTimer?.cancel()
            }
            CsNiveau.visibility = View.INVISIBLE
            RadioGroup.visibility = View.INVISIBLE
            start.setText("Reload")
            Rnum = Random.nextInt(0, 999);
            NumberEntered.setText("")
            tv_historique.setText("")
            NumberEntered.visibility = View.VISIBLE;
            check.visibility = View.VISIBLE;
            Timer.visibility = View.VISIBLE;
            Chrono.visibility=View.VISIBLE
            input.setText("");
            s="";
            result.setText("");
            if (input.getParent() != null) {
                (input.getParent() as ViewGroup).removeView(input)
            }
            countDownTimer=object : CountDownTimer(60000, 1000) {

                // Callback function, fired on regular interval
                override fun onTick(millisUntilFinished: Long) {
                    Timer.setText("" + millisUntilFinished / 1000)

                    //check the entered number*****
                    check.setOnClickListener {

                        val enteredNum = NumberEntered.text.toString();
                        if (enteredNum.isNullOrEmpty())
                        {
                            Toast.makeText(applicationContext,
                                "Tapez un nombre valide", Toast.LENGTH_SHORT).show()
                        }
                        else{
                        nbEssai++
                        s += enteredNum + "\n";
                            tv_historique.setText(s);
                            result.visibility =View.VISIBLE


                        if (!enteredNum.equals("") && Rnum < Integer.parseInt(enteredNum)) {
                            result.setText("Le nombre est plus petit que : " + enteredNum)
                            NumberEntered.setText("")
                            Toast.makeText(
                                applicationContext,
                                "resultat!" + Rnum,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!enteredNum.equals("") && Rnum > Integer.parseInt(enteredNum)) {
                            result.setText("Le nombre est plus grand que : " + enteredNum)
                            NumberEntered.setText("")
                            Toast.makeText(
                                applicationContext,
                                "resultat!" + Rnum,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            score = (score-(nbEssai+(60-(millisUntilFinished/1000)))).toInt()
                            NumberEntered.setText("")
                            if (!enteredNum.equals("") && (score != 0) && (score > 0)){
                                result.setText("");
                                CsNiveau.visibility = View.VISIBLE
                                RadioGroup.visibility = View.VISIBLE
                                builder.setTitle("                        Bravo !  ")
                                builder.setMessage("Vous avez terminé dans :"+(60-(millisUntilFinished/1000))+" second(s) \nTentatives(s) : "+nbEssai+" \nScore : "+score)
                                input.setHint("                        Tapez votre nom")
                                input.inputType = InputType.TYPE_CLASS_TEXT
                                builder.setView(input)


                                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                    var m_Text = input.text.toString()
                                    Toast.makeText(applicationContext,
                                        "Play again "+m_Text, Toast.LENGTH_SHORT).show()
                                    var cv = ContentValues()

                                    cv. put ("UNAME", m_Text)
                                    cv.put ("SCORE", score)

                                    db.insert ( "USERS", null, cv)
                                    afficheScore()
                                    start.setText("NOUVELLE PARTIE")
                                }



                                builder.show()
                           countDownTimer?.cancel()
                                isRunning=false
                                NumberEntered.setText("")
                                NumberEntered.visibility = View.INVISIBLE;
                            check.visibility = View.INVISIBLE;
                            nbEssai = 0
                                tv_historique.setText("")
                                Timer.visibility=View.INVISIBLE
                                Chrono.visibility=View.INVISIBLE
                        }
                        else{
                                result.setText("you lose")
                                tv_historique.setText("Historique")

                                NumberEntered.setText("")
                                NumberEntered.visibility = View.INVISIBLE;
                                check.visibility = View.INVISIBLE;

                                nbEssai = 0
                                result.setText("");


                            }

                        }

                    }
                    }
                }


                // Callback function, fired
                // when the time is up
                override fun onFinish() {
                    isRunning=false
                    result.setText("you lose")
                    tv_historique.setText("Historique")
                    NumberEntered.setText("")
                    NumberEntered.visibility = View.INVISIBLE;
                    check.visibility = View.INVISIBLE;
                    Timer.setText("00")
                    start.setText("NOUVELLE PARTIE")

                    looser.visibility =View.VISIBLE
                    nbEssai = 0
                    result.setText("");
                    Handler(Looper.getMainLooper()).postDelayed({
                        looser.visibility = View.GONE
                    }, AnimationUtil.SUCCESS_DISPLAY.toLong())
                    CsNiveau.visibility = View.VISIBLE
                    RadioGroup.visibility = View.VISIBLE
                }
            }.start()
        }
        intentScore.setOnClickListener {
        val intent = Intent(this, ScoreActivity::class.java)
        startActivity(intent)
        }
    }
}


