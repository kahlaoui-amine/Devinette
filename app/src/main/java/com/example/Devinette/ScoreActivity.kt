package com.example.Devinette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.Devinette.R

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        var helper=MyDBHelper(applicationContext)
        val topScore=findViewById<TextView>(R.id.topScore)
        val goBackBtn=findViewById<ImageButton>(R.id.goBack)

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

    goBackBtn.setOnClickListener(){
        onBackPressed()
    }}
}