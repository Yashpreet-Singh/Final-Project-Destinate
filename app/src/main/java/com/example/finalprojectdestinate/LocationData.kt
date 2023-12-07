package com.example.finalprojectdestinate

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class LocationData {

    //val locationList: ArrayList<DataContainer> = Gson().fromJson(locationData, object : TypeToken<ArrayList<DataContainer>>() {}.type)
    private val gson = GsonBuilder().setLenient().create()
    //val locationList: DataContainer = gson.fromJson(locationData, DataContainer::class.java)
    val locationList: ArrayList<DataContainer> = gson.fromJson(locationData, object : TypeToken<ArrayList<DataContainer>>() {}.type)

}

val locationData = """
    [
    
        { 
    
            "tripData" : 
            [
                    {   
                       "location": "California",
                       "Overview": "This is a good state",
                       "tourism_vid": [
                            0,
                            1,
                            2,
                            3
                       ],
                       "activities" :[
                            "Hiking",
                            "Sightseeing",
                       ]
                    }
            ],
            
            "userData":
            [
                {
                    "firstname": "Yash",
                    "lastname": "Singh",
                    "username" : "ypsingh200@gmail.com",
                    "passsword": "xyz",
                    "title"    : "I didn't enjoy the place",
                    "myposts" : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWdB260xE2AnVBPMWp-xgwU1lu4qhM7VHV0l8paSGp3hkOjUUml52P7Ewg7gKkyPz4Ah4&usqp=CAU"
                },
                {
                    "firstname": "Tabish",
                    "lastname": "Singh",
                    "username" : "ypsingh200@gmail.com",
                    "passsword": "xyz",
                    "title"    : "I enjoyed the place",
                    "myposts" : "https://media.cntraveler.com/photos/61eae2a9fe18edcbd885cb01/1:1/w_3031,h_3031,c_limit/Seychelles_GettyImages-1169388113.jpg"
                }
            ]
        }
        
    ]           
    
               

""".trimIndent()



//data class TripData(
//    @SerializedName("location") val location: String?,
//    @SerializedName("db_id") var dbid: Int = -1, // db primary key
//
//    @SerializedName("overview") val overview: String?,
//    @SerializedName("tourism_vid") val toursimVid: List<String>?,
//    //@SerializedName("activities") val activities: List<String>?,
//
//    //user
//    @SerializedName("user") val user: String?,
//    @SerializedName("db_id_user") var dbidUser: Int = -1,
//    @SerializedName("password") val password: String?,
//    @SerializedName("myposts") val myposts: List<String>?,
//
//
//    ) : Serializable

data class TripData(
    @SerializedName("location") val location: String?,
    @SerializedName("Overview") val overview: String?,
    @SerializedName("tourism_vid") val tourismVid: List<String>?,
    @SerializedName("db_tid") var dbtid: Int = -1, // db primary key

    //@SerializedName("activities") val activities: List<String>?
) : Serializable

data class UserData(
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("passsword") val password: String?,
    @SerializedName("myposts") val myposts: String?,
    @SerializedName("is_liked") var isLiked:  Boolean?,
    @SerializedName("title") var title:  String?,
    @SerializedName("db_uid") var dbuid: Int = -1, // db primary key
) : Serializable

data class DataContainer(
    @SerializedName("tripData") val tripData: ArrayList<TripData>?,
    @SerializedName("userData") val userData: ArrayList<UserData>?
) : Serializable
