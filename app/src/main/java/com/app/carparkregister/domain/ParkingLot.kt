package com.app.carparkregister.domain

class ParkingLot(

        var lotId: String = "",
        var takenBy: UserDao? = null,
        var carParked: CarDao? = null

)