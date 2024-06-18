package com.studies.shashank_assignment.Models

import java.io.Serializable

class entry :Serializable{
    var x:Float?=null
    var y:Float?=null
    constructor()
    constructor( x:Float, y:Float){
        this.x=x
        this.y=y
    }
}