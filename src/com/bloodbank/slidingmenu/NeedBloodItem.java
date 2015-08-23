package com.bloodbank.slidingmenu;

public class NeedBloodItem {
    public String name;
    public String email;
    public String gender;
    public String bgroup;
    public String phone;
    public String country;
    public String state;
    public String city;
    public String address;
    public String description;
    public String weight;
    public String age;
    public String distance;

    
    public NeedBloodItem(){
        super();
    }
   
    public NeedBloodItem(String name,String email,String gender,
    		String bgroup,String phone,String country,
    		String state, String city, String address, String description, 
    		String weight, String age , String distance) {
        super();
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.bgroup = bgroup;
        
        this.phone = phone;
        this.country = country;
        
        this.state = state;
        this.city = city;
        
        this.address = address;
        this.description = description;
        
        this.weight = weight;
        this.age = age;
        this.distance = distance;
    }
    
    public NeedBloodItem(String name,String email,String phone)
    {
    	super();
        this.name = name;
        this.email = email;
        this.phone=phone;
    }
}