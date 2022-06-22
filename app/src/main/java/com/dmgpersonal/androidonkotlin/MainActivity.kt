package com.dmgpersonal.androidonkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class MainActivity : AppCompatActivity() {

    private val dataClass = SimpleTestClass(1, "Second field")
    private lateinit var copyDataClass: SimpleTestClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        findViewById<AppCompatButton>(R.id.showDataClassFieldButton)
            .setOnClickListener { showDataClass(dataClass) }
        findViewById<AppCompatButton>(R.id.showCopyDataClassFieldButton)
            .setOnClickListener { showCopyDataClass() }
        findViewById<AppCompatButton>(R.id.foreachButton)
            .setOnClickListener { forEachCycle() }
        findViewById<AppCompatButton>(R.id.repeatButton)
            .setOnClickListener { repeatCycle() }
        findViewById<AppCompatButton>(R.id.forButton)
            .setOnClickListener { forCycle() }
        findViewById<AppCompatButton>(R.id.whileButton)
            .setOnClickListener { whileCycle() }
        findViewById<AppCompatButton>(R.id.doWhileButton)
            .setOnClickListener { doWhileCycle() }
    }

    private fun doWhileCycle() {
        val res = ArrayList<Char>()
        var ch = 'J'
        while (ch != 'A') {
            if(ch == 'G') {
                res.add('g')
                --ch
                continue
            }
            res.add(ch--)
        }
        findViewById<AppCompatTextView>(R.id.whileCycleTextView)
            .text = res.toString()
    }

    private fun whileCycle() {
        val res = ArrayList<Char>()
        var ch = 'A'
        while (ch != 'J') {
            res.add(ch++)
        }
        findViewById<AppCompatTextView>(R.id.whileCycleTextView)
            .text = res.toString()
    }

    private fun forCycle() {
        val res = ArrayList<Int>()
        for (i in 1..9) {
            res.add(i)
        }
        findViewById<AppCompatTextView>(R.id.forCycleTextView)
            .text = res.toString()
    }

    private fun repeatCycle() {
        val res = ArrayList<Int>()
        repeat(9) { index -> res.add(index) } // it переименовываем в index для наглядности
        findViewById<AppCompatTextView>(R.id.repeatCycleTextView)
            .text = res.toString()
    }

    private fun forEachCycle() {
        val mass = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val forEachCycleTextView = findViewById<AppCompatTextView>(R.id.foreachCycleTextView)
        // млжно было бы просто присвоить mass.toString()
        val st = StringBuilder()
        mass.forEach { st.append(it) }
        forEachCycleTextView.text = mass.toString()
    }

    private fun showCopyDataClass() {
        copyDataClass = dataClass.copy(firstFieldIdx = 2)
        findViewById<AppCompatTextView>(R.id.dataClassCopyFieldOneTextView)
            .text = copyDataClass.firstFieldIdx.toString()
        findViewById<AppCompatTextView>(R.id.dataClassCopyFieldTwoTextView)
            .text = copyDataClass.someText
    }

    private fun showDataClass(dataClass: SimpleTestClass) {
        findViewById<AppCompatTextView>(R.id.dataClassFieldOneTextView)
            .text = dataClass.firstFieldIdx.toString()
        findViewById<AppCompatTextView>(R.id.dataClassFieldTwoTextView).text = dataClass.someText
    }
}