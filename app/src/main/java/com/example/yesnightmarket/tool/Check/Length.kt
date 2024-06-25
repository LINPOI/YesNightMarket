package com.example.yesnightmarket.tool.Check

fun Length(vararg s:String,size:Int=6):Boolean{
    s.forEach {
        if(it.length<size)
            return false
    }
    return true
}
fun Length(s:String,size:Int=6):Boolean{
    return s.length >= size
}