import com.app.carparkregister.domain.CarDao

public class StoredCars private constructor() {
    init {
    }

    private object Holder {
        val INSTANCE = StoredCars()
    }

    companion object {
        val instance: StoredCars by lazy { Holder.INSTANCE }
    }

    private var storedCars: ArrayList<CarDao> = arrayListOf(CarDao())

    public fun getStoredCars(): ArrayList<CarDao> {
        return storedCars
    }

    public fun setStoredCars(storedCars: ArrayList<CarDao>) {
        this.storedCars = storedCars
    }

}