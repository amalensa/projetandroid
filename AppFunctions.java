package com.example.tastymix;

public class AppFunctions {
    public static double average(double ratingAVG)
    {
        double floorRating = Math.floor(ratingAVG);
        double difference = ratingAVG - floorRating;
        if(difference >=0 && difference < 0.25 ){
            return floorRating;
        }else if (difference >=0.25 && difference < 0.75 )
        {
            return floorRating +0.5;
        }else{
            return floorRating + 1;
        }
    }
}
