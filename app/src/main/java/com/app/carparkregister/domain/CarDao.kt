package com.app.carparkregister.domain

class CarDao(
        val model: String = "",
        val color: String = "",
        val spz: String = ""
) {
    override fun toString(): String {
        return color + " " + model + " - " + spz
    }
}