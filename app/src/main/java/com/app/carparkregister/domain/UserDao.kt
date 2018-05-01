package com.app.carparkregister.domain

data class UserDao(
        val email: String = "",
        val fullName: String = "",
        val phone: String = "",
        val cars: ArrayList<CarDao>? = null
)
