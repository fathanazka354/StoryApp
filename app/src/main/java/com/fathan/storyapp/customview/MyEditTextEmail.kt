package com.fathan.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.fathan.storyapp.R

class MyEditTextEmail:AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    private fun init(){
        addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotEmpty()){
                    if(emailValidation(p0.toString())) setError(null) else setError(context.getString(
                        R.string.email_is_invalid))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
    private fun emailValidation(s:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
    }
}