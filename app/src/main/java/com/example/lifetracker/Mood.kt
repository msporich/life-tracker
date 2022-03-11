package com.example.lifetracker

/*

Mood Object

Author: Mark Sporich, #200399323
Description: For use in adding a Mood entry to the database.
             Use with the Mood Tracker activities.

 */

class Mood (

    var id: String?=null,
    var userId: String?=null,
    var moodType: String?=null,
    var dateCreated: String?=null

)