package com.app.carparkregister.domain

data class UserDao(
        val email: String = "",
        val fullName: String = "",
        val phone: String = "",
        var cars: ArrayList<CarDao>? = null
){
    override fun toString(): String {
        return fullName + ", " + phone + ", " + email
    }
}
