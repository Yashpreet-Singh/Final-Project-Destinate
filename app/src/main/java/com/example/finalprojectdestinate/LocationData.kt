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
                       "location": "Syracuse",
                       "Overview": "Syracuse is a city and the county seat of Onondaga County, New York, United States. With a population of 148,620 and a metropolitan area of 662,057,[6] it is the fifth-most populated city and 13th-most populated municipality in the state of New York.",
                       "tourism_vid": [
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ZZzUX7ukYdA?si=YBUOno2oZlbt3-jn\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/QpgBFNeoT0o?si=1sC5ZvikKsflcElZ\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/BZfn_3xYIMs?si=ve32rqy2CnWiLdVS\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>",
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/ARohgpPL_PY?si=4nc0zp-xUxjSCH0K\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                       ],
                       "activities" :[
                            "Hiking",
                            "Sightseeing",
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
                            "Hiking",
                            "Sightseeing",
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
                },
                {
                    "firstname": "Tabishoip",
                    "lastname": "Singh",
                    "username" : "yp",
                    "passsword": "xyz",
                    "title"    : "I ahte the place",
                    "myposts" : "https://images.pexels.com/photos/268533/pexels-photo-268533.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
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
