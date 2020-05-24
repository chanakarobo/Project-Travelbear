package com.example.travelbear;

import android.app.Activity;

public class TopImages  {

    private String name;
    private int imageResourceId;




    public static final TopImages[] topimages={

      new TopImages("We provide best locations you want to travel. You can view relevant details and can select places you want to visit. You can get to know about the unpopular but amazing  places around. ",R.drawable.locations),
      new TopImages("We provide best guiders you want. Discover, plan and book your perfect trip with expert advice and qualified travel guides. And we give you the chance to book guides by yourself.   ",R.drawable.guiders),
      new TopImages("You can save your favorite locations. And you can save the places you have already been. Make your own list of favorite locations and a list of been locations.",R.drawable.favorites),
      new TopImages("You can share your ideas with everyone. Comment about the places you visited and give your feedbacks to our guides. Hope you will enjoy the time with us.",R.drawable.guidersdetail)


    };

    private TopImages(String name,int imageResourceId){

        this.name=name;
        this.imageResourceId=imageResourceId;

    }

    public String getName(){
        return  name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }




}
