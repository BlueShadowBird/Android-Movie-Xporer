package id.web.dedekurniawan.moviexplorer.core.utils

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun numberFormat(number: Float?): String = DecimalFormat("#,###,###.##").format(number)

private val sdf = SimpleDateFormat("yyyy-MM-DD", Locale.US)

@RequiresApi(Build.VERSION_CODES.O)
private val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd")

class Date : Parcelable {
    var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    constructor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDate = LocalDate.now()

            year = localDate.year
            month = localDate.monthValue
            day = localDate.dayOfMonth
        } else {
            val calendar = Calendar.getInstance()

            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
        }
    }

    constructor(parcel: Parcel) {
        year = parcel.readInt()
        month = parcel.readInt()
        day = parcel.readInt()
    }


    fun parseDate(dateString: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val localDate = LocalDate.parse(
            dateString,
            dtf
        )
        year = localDate.year
        month = localDate.monthValue
        day = localDate.dayOfMonth
    } else {
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(dateString) as java.util.Date
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH]
        day = calendar[Calendar.DAY_OF_MONTH]
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(year)
        dest.writeInt(month)
        dest.writeInt(day)
    }

    override fun toString(): String {
        return "$year/$month/$day"
    }

    companion object CREATOR : Parcelable.Creator<Date> {
        override fun createFromParcel(parcel: Parcel) = Date(parcel)

        override fun newArray(size: Int): Array<Date?> = arrayOfNulls(size)
    }
}

fun String.toDate() = Date().apply { parseDate(this@toDate) }