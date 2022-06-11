package Model

import android.os.Parcel
import android.os.Parcelable

class User (
    var id: String,
    var username: String,
    var email: String,
): Subject(id, username), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    ){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }




//    fun toMap(): Any {
//        return mapOf(
//            "uid" to id,
//            "username" to username,
//            "email" to email,
//        )
//    }


    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}