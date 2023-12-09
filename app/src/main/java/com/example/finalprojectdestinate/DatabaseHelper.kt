package com.example.finalprojectdestinate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    companion object {
        private val DB_NAME = "myTravelDatabase.db"
        private val DB_VER = 1
        private val COL_LOCATION = "location"
        private val COL_TID = "dbtid" //unique id for location table
        private val COL_OVERVIEW = "overview"

        //private val COL_ACTIVITES = "tourism_vid"

        private val COL_TOURISIM1 = "tourism_vid1"
        private val COL_TOURISIM2 = "tourism_vid2"
        private val COL_TOURISIM3 = "tourism_vid3"
        private val COL_TOURISIM4 = "tourism_vid4"


        private val COL_UID = "dbuid" //unique id for user table
        private val COL_FIRSTNAME = "firstname"
        private val COL_LASTNAME = "lastname"
        private val COL_USERNAME = "username"
        private val COL_PASSWORD = "password"
        private val COL_LIKED = "is_liked"
        private val COL_POST = "mypost"
        private val COL_DESCRIPITION = "title"


        // create table LocationData
        private val CREATE_TABLE_LOCATION = "CREATE TABLE IF NOT EXISTS LocationTable " +
                "( $COL_TID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_LOCATION TEXT, " +
                "$COL_OVERVIEW TEXT,$COL_TOURISIM1 TEXT, $COL_TOURISIM2 TEXT, $COL_TOURISIM3 TEXT, $COL_TOURISIM4 TEXT )"


        private val CREATE_TABLE_USER_TABLE = "CREATE TABLE IF NOT EXISTS UserTable ( $COL_UID INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "$COL_FIRSTNAME TEXT, $COL_LASTNAME TEXT, $COL_USERNAME TEXT, $COL_PASSWORD TEXT, $COL_POST TEXT, $COL_LIKED BOOLEAN ,$COL_DESCRIPITION TEXT )"


        private val DROP_TABLE_LOCATION = "DROP TABLE IF EXISTS LocationTable"
        private val DROP_TABLE_USER_TABLE = "DROP TABLE IF EXISTS UserTable "

    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create tables on database creation
        db.execSQL(CREATE_TABLE_LOCATION)
        db.execSQL(CREATE_TABLE_USER_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade database if needed
        db.execSQL(DROP_TABLE_LOCATION)
        db.execSQL(DROP_TABLE_USER_TABLE)

        //onCreate(db)
    }

    fun closeDB () {
        val db = this.readableDatabase
        if (db != null && db.isOpen)
            db.close()

    }


    fun initializeTables() {
        val db = this.readableDatabase

        var query: String = "SELECT * FROM UserTable"

        var c = db.rawQuery(query, null)

        if (c.count <= 0) {
            insertAllUsers()
        }

        c.close()

        query = "SELECT * FROM LocationTable"

        c = db.rawQuery(query, null)

        if (c.count <= 0) {
            insertAllPlaces()
        }
        c.close()
    }

    fun insertAllUsers() {
        val myStaticMovie = LocationData()

        //consider [[trip:{0:{},1:{},2:{}},user:{}],[trip;{},user:{}]]
        //locationlist[0] for tripdata , 1 for user data

        for (user in myStaticMovie.locationList) {
            Log.i("dtaa",user.toString())
            for (singleUser in user.userData!!){
                Log.i("dtaa",singleUser.toString())
                addUsers(singleUser)
            }
        }

    }

    fun insertAllPlaces() {
        val myStaticMovie = LocationData()

        for (place in myStaticMovie.locationList) {
            for (singleLoc in place.tripData!!) {
                addPlace(singleLoc)
            }

        }
    }


    fun addUsers(user: UserData) {

        val db = this.writableDatabase
        //add using to usertable
        val values = ContentValues()
        values.put(COL_FIRSTNAME, user.firstname)
        values.put(COL_LASTNAME, user.lastname)
        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_POST, user.myposts)
        values.put(COL_DESCRIPITION, user.title)
        values.put(COL_LIKED, 0)//not liked



        db.insert("UserTable", null, values)

    }


    fun addPlace(place: TripData): Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COL_LOCATION, place.location)
        //values.put(COL_ACTIVITES, place.location)
        values.put(COL_OVERVIEW, place.overview) //id columns will be generated automatically

        values.put(COL_TOURISIM1, place.tourismVid!![0])
        values.put(COL_TOURISIM2, place.tourismVid[1])
        values.put(COL_TOURISIM3, place.tourismVid[2])
        values.put(COL_TOURISIM4, place.tourismVid[3])


        return db.insert("LocationTable", null, values)
    }

//    fun addGenre(type: Int, genre: String): Long {
//        val db = this.writableDatabase
//
//        val values = ContentValues()
//        values.put(COL_ID, type)
//        values.put(COL_GENRE, genre)
//
//        return db.insert(GENRES_TABLE, null, values)
//    }

    //Fetch a movie
    /*
    fun getMovie(mid: Int): MovieData? {
        val db = this.readableDatabase

        val query = "SELECT * FROM movies WHERE $COL_ID = $mid"

        val c = db.rawQuery(query, null)

        if (c.moveToFirst()) {
            return MovieData(
                id = c.getInt(c.getColumnIndex(COL_ID)),
                voteAverage = c.getDouble(c.getColumnIndex(COL_VOTE_AVG)),
                title = c.getString(c.getColumnIndex(COL_TITLE)),
                originalTitle = c.getString(c.getColumnIndex(COL_ORG_TITLE)),
                originalLanguage = c.getString(c.getColumnIndex(COL_ORG_LANG)),
                overview = c.getString(c.getColumnIndex(COL_OVERVIEW)),
                popularity = c.getDouble(c.getColumnIndex(COL_POPULARITY)),
                posterPath = c.getString(c.getColumnIndex(COL_POSTER)),
                backdropPath = c.getString(c.getColumnIndex(COL_BACKDROP)),
                voteCount = c.getInt(c.getColumnIndex(COL_VOTE_CNT)),
                releaseDate = c.getString(c.getColumnIndex(COL_RELEASE)),
                //genreIds = c.get(c.getColumnIndex(COL_RELEASE))
            )
        } else {
            return null
        }
    }*/


    //Delete a movie
    fun deleteUser(id: Int) {
        val db = this.writableDatabase
        db.delete("UserTable", "$COL_UID = ?", arrayOf(id.toString()))
        db.close()
    }

//    //Delete all checked movies
//    fun deleteAllMovies(){
//        val db = this.writableDatabase
//
//        //val query = "DELETE FROM MoviesTable WHERE  checked = 1"
//
//        //db.execSQL(query)
//        //db.close()
//        db.delete("MoviesTable", "$COL_CHECKED = ?", arrayOf("1"))
//        db.close()
//
//
//    }




    //Fetch all location Data
    fun getAllLocationData(): ArrayList<TripData>{

        val db = this.readableDatabase
        val getLocationList = ArrayList<TripData>()

        val query = "SELECT * FROM LocationTable "

        val c = db.rawQuery(query, null)

        while (c.moveToNext()) {//iterate over all rows
            val tourismList = mutableListOf<String?>().apply {
                add(c.getString(with(c) { getColumnIndex(COL_TOURISIM1) }))
                add(c.getString(with(c) { getColumnIndex(COL_TOURISIM2) }))
                add(c.getString(with(c) { getColumnIndex(COL_TOURISIM3) }))
                add(c.getString(with(c) { getColumnIndex(COL_TOURISIM4) }))
            } //for every row startempty

            val locationInfo = TripData(
                dbtid = c.getInt(with(c) { getColumnIndex(COL_TID) }), //unquie id
                overview = c.getString(with(c) { getColumnIndex(COL_OVERVIEW) }),
                location = c.getString(with(c) { getColumnIndex(COL_LOCATION) }),
                tourismVid = tourismList.toList().filterNotNull()

            )
            getLocationList.add(locationInfo)

        }
        c.close()
        return getLocationList

    }

    //Fetch all User Data
    fun getAllUserData(): ArrayList<UserData>{

        val db = this.readableDatabase
        val getUserList = ArrayList<UserData>()

        val query = "SELECT * FROM UserTable "

        val c = db.rawQuery(query, null)

        while (c.moveToNext()) {//iterate over all rows
            val UserInfo = UserData(
                dbuid = c.getInt(with(c) { getColumnIndex(COL_UID) }), //unquie id
                firstname = c.getString(with(c) { getColumnIndex(COL_FIRSTNAME) }),
                lastname = c.getString(with(c) { getColumnIndex(COL_LASTNAME) }),
                username = c.getString(with(c) { getColumnIndex(COL_USERNAME) }),
                password = c.getString(with(c) { getColumnIndex(COL_PASSWORD) }),
                isLiked = c.getInt(with(c) { getColumnIndex(COL_LIKED) }) == 1, //returns true or false
                myposts = c.getString(with(c) { getColumnIndex(COL_POST) }),
                title =  c.getString(with(c) { getColumnIndex(COL_DESCRIPITION) })

            )
            getUserList.add(UserInfo)

        }
        c.close()
        return getUserList
    }



    //update a movie -on check status

    fun updateLikedUser(user: UserData) {
        Log.d("Update done","done")
        val db = this.writableDatabase

        val values = ContentValues()

        Log.i("on database",user.toString())

        values.put(COL_LIKED, if (user.isLiked == true) 1 else 0)

        db.update("UserTable", values, "$COL_UID = ?", arrayOf(user.dbuid.toString()))

        db.close()
    }


    fun addNewUser(email: String, password: String, firstname :String,lastname:String){

        val db =this.writableDatabase

        //add using to usertable
        val values = ContentValues()
        values.put(COL_FIRSTNAME, firstname)
        values.put(COL_LASTNAME, lastname)
        values.put(COL_USERNAME, email)
        values.put(COL_PASSWORD, password)
        //values.put(COL_POST, user.myposts)
        //values.put(COL_DESCRIPITION, user.title)
        values.put(COL_LIKED, 0)//not liked

        db.insert("UserTable", null, values)



    }








}
