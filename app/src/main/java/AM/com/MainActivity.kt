package AM.com

import android.content.Intent
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    var word: String = ""
    var hidden = ""
    var wordT = ArrayList<Boolean>()

    private val maxtry: Int = 7
    var actry: Int = 0


    private var blocks = arrayOfNulls<Button>(26)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        for (position in 1..26) {
            val resId = resources.getIdentifier("button$position", "id", packageName)
            var letter = findViewById<View>(resId) as Button
            blocks[position-1] = letter
            letter.setOnClickListener {
                makeMove(position,it)
            }
        }


        btnReset.setOnClickListener(View.OnClickListener {
            val starter = intent
            finish()
            starter.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(starter)
        })

        getWord()

    }

    private fun makeMove(position: Int, view: View) {
        var lett = (64+position).toChar()
        var btn = view as Button

        if(word.contains(lett,ignoreCase = true)){
            for(i in 0..word.length-1){
                if(word[i] == lett){
                   wordT[i]=true
                }
            }

            reHidden()

            btn.setTextColor(GREEN)
            btn.isEnabled=false
        } else
        {
            val img = resources.getIdentifier("error$actry", "drawable", packageName)
            actry+=1
            println(actry)
            imageView.setImageResource(img)
            btn.setTextColor(RED)
            btn.isEnabled=false
        }

        checkWin()

    }

    private fun checkWin() {
        if(!wordT.contains(false)){
            myword2.text="YOU WIN"
            myword.setTextColor(GREEN)
            myword2.setTextColor(GREEN)
            for (i in blocks) i!!.isEnabled=false
        } else {
            if(actry==maxtry){
                myword.text=word
                myword.setTextColor(RED)
                myword2.text="YOU LOSE"
                myword2.setTextColor(RED)
                for (i in blocks) i!!.isEnabled=false
            }

        }
    }

    private fun reHidden() {
        var x =""
        for(i in 0..word.length-1){
            if(wordT[i]){
               x = x + word[i]+ " "
            } else {
                x += "_ "
            }
        }
        myword.text = x
    }


    private fun getWord() {
        val wordsList: Array<String> = resources.getStringArray(R.array.guessWords)
        val rand = Random().nextInt(wordsList.size)
         word = wordsList[rand].toUpperCase()
         for(i in 1..word.length){
             hidden += "_ "
             wordT.add(false)
         }
        myword.text=hidden
    }


}
