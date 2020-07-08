package com.example.passguard.util

import android.util.Log
import android.util.Patterns

class Validate
{
    companion object
    {
        fun isEmailValid(email : String?) : Int
        {
            Log.d("EMAIL", "email: $email")

            if (email.isNullOrEmpty())
                return 1

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                return 2

            return 0
        }

        fun isPasswordValid(password : String?) : Int
        {
            if (password.isNullOrEmpty())
                return 1

            if (password.length < 8)
                return 2

            return 0
        }
    }
}