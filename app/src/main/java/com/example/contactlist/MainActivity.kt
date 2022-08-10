package com.example.contactlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obj = BaseImpl2()
        obj.sayHello()
    }
}

interface Base {
    fun sayHello()
    fun logger(text: String)
}

class BaseImpl : Base {
    override fun sayHello() {
        Log.d("DELEGATE", "hello from baseimpl")
    }

    override fun logger(text: String) {
        Log.d("DELEGATE", text)
    }
}

class BaseImpl2: Base by BaseImpl() {
//    override fun sayHello() {
//        Log.d("DELEGATE", "hello from baseimpl 2")
//    }
//
//    override fun logger(text: String) {
//        Log.d("DELEGATE", text)
//    }

    fun test() {
        Log.d("DELEGATE", "Just another method")
    }

}