package com.example.passguard.session

import com.example.passguard.model.User

class Session private constructor(private var user: User?, private var authenticate : Boolean) {

    companion object
    {
        private var instance : Session? = null

        fun getInstance() : Session
        {
            if (instance == null)
                instance = Session(null, false)

            return instance!!
        }

        fun saveInstance(user: User)
        {
            instance = Session(user, true)
        }

        fun destroy()
        {
            if (instance != null)
                instance = Session(null, false)
        }

        fun update(user: User)
        {
            instance!!.user = user
        }
    }

    fun getUser() = this.user

    fun getAuthentication() = this.authenticate
}