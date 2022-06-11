package Database

import Model.Classroom
import Model.User

class GlobalVar {
    companion object {
        lateinit var user: User
        var classArrayList = ArrayList<Classroom>()
    }
}