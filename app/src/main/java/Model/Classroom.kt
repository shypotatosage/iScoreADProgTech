package Model

import android.os.Parcel
import android.os.Parcelable

class Classroom (
    var id: String = "",
    var name: String = "",
    var desc: String = "",
    var students: ArrayList<Student> = arrayListOf()
): Subject(id, name), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("students")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(desc)
    }

    var imageUri: String = ""

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Classroom> {
        override fun createFromParcel(parcel: Parcel): Classroom {
            return Classroom(parcel)
        }

        override fun newArray(size: Int): Array<Classroom?> {
            return arrayOfNulls(size)
        }
    }

}