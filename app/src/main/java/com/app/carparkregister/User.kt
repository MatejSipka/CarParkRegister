package com.app.carparkregister

import com.google.firebase.database.DatabaseReference


data class User(
        val name: String = "",
        val car: String = "")

fun loadDatabase(firebaseData: DatabaseReference) {
    val availableUsers: List<User> = mutableListOf(
            User("Honza", "Porsche"),
            User("Peter", "Toyota"),
            User("Pepa", "Peugeot"),
            User("Matej", "Nissan")
    )
    availableUsers.forEach {
        val key = firebaseData.child("users").push().key

        firebaseData.child("users").child(key).setValue(it)
    }
}
