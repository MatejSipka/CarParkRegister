package com.app.carparkregister.domain

class ParkingLot(

        val lotId: String = "",
        val taken: Boolean = false,
        val takenBy: UserDao? = null,
        val carParked: CarDao? = null

)