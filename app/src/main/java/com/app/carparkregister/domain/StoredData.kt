import com.app.carparkregister.domain.CarDao
import com.app.carparkregister.domain.UserDao
import com.app.carparkregister.domain.WeekDays

public class StoredData private constructor() {
    init {
    }

    private object Holder {
        val INSTANCE = StoredData()
    }

    companion object {
        val instance: StoredData by lazy { Holder.INSTANCE }
    }

    private var storedCars: ArrayList<CarDao> = arrayListOf(CarDao())
    private var user: UserDao? = null
    private var daySelected: WeekDays = WeekDays.MON

    public fun getDaySelected(): WeekDays {
        return daySelected
    }

    public fun setDaySelected(day: WeekDays) {
        this.daySelected = day
    }

    public fun getUser(): UserDao? {
        return user
    }

    public fun setUser(user: UserDao) {
        this.user = user
    }

    public fun getStoredCars(): ArrayList<CarDao> {
        return storedCars
    }

    public fun setStoredCars(storedCars: ArrayList<CarDao>) {
        this.storedCars = storedCars
    }

}