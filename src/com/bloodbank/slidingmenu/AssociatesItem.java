package com.bloodbank.slidingmenu;

class AssociatesItem {
    public String id;
    public String name;
    public String state;
    public String city;
    public String contact;
    public String personName;
    public String personPhone;
    public String timing;
    public String storageType;
    public String createdDate;
    public String modifiedDate;
    
    private AssociatesItem(){
        super();
    }
   
    public AssociatesItem(String id, String name, String state,String city,String contact
    		,String personName,String personPhone,String timing,String storageType ,
    		String createdDate,String modifiedDate) 
    {
        super();
        this.id = id;
        this.name = name;
        this.city = city;
        this.contact = contact;
        this.personName = personName;
        this.personPhone = personPhone;
        this.timing = timing;
        this.storageType = storageType;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        
    }
    public AssociatesItem(String name, String city,String contact)
    {
    	this.name = name;
    	this.city = city;
    	this.contact = contact;
    }
}

