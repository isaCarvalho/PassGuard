package com.example.passguard.util

import android.util.Log
import android.util.Patterns

class Validate
{
    companion object
    {
        fun isEmailValid(email : String?) : String?
        {
            Log.d("EMAIL", "email: $email")

            if (email.isNullOrEmpty())
                return "Email é obrigatório"

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                return "Email inválido"

            return null
        }

        fun isPasswordValid(password : String?) : String?
        {
            if (password.isNullOrEmpty())
                return "Senha é obrigatório"

            if (password.length < 8)
                return "Senha inválida"

            return null
        }
    }
}