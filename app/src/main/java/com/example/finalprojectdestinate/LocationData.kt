package com.example.finalprojectdestinate

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.util.Base64


class LocationData {

    //val locationList: ArrayList<DataContainer> = Gson().fromJson(locationData, object : TypeToken<ArrayList<DataContainer>>() {}.type)
    private val gson = GsonBuilder().setLenient().create()
    //val locationList: DataContainer = gson.fromJson(locationData, DataContainer::class.java)
    val locationList: ArrayList<DataContainer> = gson.fromJson(locationData, object : TypeToken<ArrayList<DataContainer>>() {}.type)
    //var  modifiedLocationList : ArrayList<DataContainer>
    var defaulTProfileTable: MutableMap<String, Int> = mutableMapOf()



    init {

        defaulTProfileTable = mutableMapOf()
        defaulTProfileTable["Yash"] = R.drawable.yash
        defaulTProfileTable["Tabish"] = R.drawable.tabish

        Log.i("done", defaulTProfileTable["Yash"].toString())
//
//
//        for (loc in locationList) {
//            for (user in loc.userData!!) {
//                user.profileImg = convertDrawableToByteArray(defaulTProfileTable[user.firstname])
//
//                //Log.i("newimgdone1",convertDrawableToByteArray(defaulTProfileTable[user.firstname]).toString())
//                //Log.i("newimgdone2",user.profileImg.toString())
//            }
//            //Log.i("newnewlist",loc.userData.toString())
//            val updatedJsonData = Gson().toJson(jsonArray)
//            Log.d("newlistdone",updatedJsonData.toString())
//        }
//        //Log.i("newlistdone", locationList[0].userData.toString())
//        // Convert the JsonObject back to a JSON string








    }



}

val locationData = """
    [
    
        { 
    
            "tripData" : 
            [
                    {   
                       "location": "Syracuse",
                       "Overview": "Syracuse is a city and the county seat of Onondaga County, New York, United States. With a population of 148,620 and a metropolitan area of 662,057,[6] it is the fifth-most populated city and 13th-most populated municipality in the state of New York.",
                       "tourism_vid": [
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ZZzUX7ukYdA?si=YBUOno2oZlbt3-jn\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/QpgBFNeoT0o?si=1sC5ZvikKsflcElZ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/BZfn_3xYIMs?si=ve32rqy2CnWiLdVS\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ARohgpPL_PY?si=4nc0zp-xUxjSCH0K\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                       ],
                       "activities" :[
                            "Kayaking",
                             "Fishing",
                             "Cycling"
                             
                             
                       ]
                    },
                    {   
                       "location": "California",
                       "Overview": "California is a state in the Western United States. With over 38.9 million residents[6] across a total area of approximately 163,696 square miles (423,970 km2),[11] it is the most populous U.S. state, the third-largest U.S. state by area, and the most populated subnational entity in North America.",
                       "tourism_vid": [
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Z9Rs9ZFcZeM?si=gXbPwszbb942pTrD\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/mD5RwhlRNAM?si=0rk5770ttCDswR9C\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/qyR2Un2rhsA?si=UKZY_-OoGBeP0RXd\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/p2vhC8nHSwU?si=mbWGsjRt2RMV1q3o\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                       ],
                       "activities" :[
                            "Zip Lining",
                            "Horseback Riding",
                            "Geocaching"
                            
                       ]
                    },
                    {   
                       "location": "Mountain View",
                       "Overview": "Mountain View is a city in Santa Clara County, California, United States. Named for its views of the Santa Cruz Mountains, it has a population of 82,376 as of the 2020 census. Mountain View was integral to the early history and growth of Silicon Valley, and is the location of many high technology companies.",
                       "tourism_vid": [
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/x0-uqpCx7d8?si=GHeFtjp-IkxG6iQM\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/gCRNEJxDJKM?si=ejZf_FGo21m1oEHr\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/_e8BFrAPedM?si=LjiljawATbnyR_9u\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y3FHIaV8ryE?si=Es_IfnUTTYvoETvj\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                       ],
                       "activities" :[
                            "Hiking",
                            "Sightseeing",
                            "Bird Watching"
                            
                       ]
                    }
                    
                    
            ],
            
            "userData":
            [
                {
                    "firstname": "Yash",
                    "lastname": "Singh",
                    "username" : "ypsingh",
                    "profile_img": [],
                    "passsword": "xyz",
                    "title"    : "I didn't enjoy the place",
                    "myposts" : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWdB260xE2AnVBPMWp-xgwU1lu4qhM7VHV0l8paSGp3hkOjUUml52P7Ewg7gKkyPz4Ah4&usqp=CAU"
                },
                {
                    "firstname": "Tabish",
                    "lastname": "Khan",
                    "username" : "tabish",
                    "profile_img": [],
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

    @SerializedName("activities") val activities: List<String>?
) : Serializable

data class UserData(
    @SerializedName("firstname") var firstname: String?,
    @SerializedName("lastname") var lastname: String?,
    @SerializedName("username") var username: String?,
    @SerializedName("passsword") var password: String?,
    @SerializedName("myposts") var myposts: String?,
    @SerializedName("is_liked") var isLiked:  Boolean?,
    @SerializedName("profile_img") var profileImg:  ByteArray,
    @SerializedName("title") var title:  String?,
    @SerializedName("db_uid") var dbuid: Int = -1, // db primary key
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserData

        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (myposts != other.myposts) return false
        if (isLiked != other.isLiked) return false
        if (!profileImg.contentEquals(other.profileImg)) return false
        if (title != other.title) return false
        return dbuid == other.dbuid
    }

    override fun hashCode(): Int {
        var result = firstname?.hashCode() ?: 0
        result = 31 * result + (lastname?.hashCode() ?: 0)
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + (myposts?.hashCode() ?: 0)
        result = 31 * result + (isLiked?.hashCode() ?: 0)
        result = 31 * result + profileImg.contentHashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + dbuid
        return result
    }
}

data class DataContainer(
    @SerializedName("tripData") val tripData: ArrayList<TripData>?,
    @SerializedName("userData") val userData: ArrayList<UserData>?
) : Serializable



//"""
//Hiking",
//    "Camping",
//    "Cycling",
//    "Rock Climbing",
//    "Kayaking",
//    "Fishing",
//    "Canoeing",
//    "Skiing or Snowboarding",
//    "Bird Watching",
//    "Surfing",
//    "Photography",
//    "Zip Lining",
//    "Horseback Riding",
//    "Geocaching",
//    "Stargazing",
//    "White Water Rafting",
//    "Sailing",
//    "Stand-Up Paddleboarding (SUP)",
//    "Nature Walks"
//"""

