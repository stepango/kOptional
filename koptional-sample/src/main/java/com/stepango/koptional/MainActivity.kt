package com.stepango.koptional

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById(R.id.text) as TextView

        textView.text = arrayOf(null, "1", null, "2", null, "3")
                .map { it.toOptional() }
                .mapIndexed { i, optional -> optional.ifEmpty { Log.e("kOptional", "$i element is null, it's bad") } }
                .map { it.map { "$it is not null " } }
                .fold(StringBuffer()) { buffer, os ->
                    buffer.apply { os.ifPresent { append("$it\n") } }
                }

    }
}
