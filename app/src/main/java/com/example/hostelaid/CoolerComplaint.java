package com.example.hostelaid;

public class CoolerComplaint {
    private String hostel;
    private String floor;
    private String coolerNumber;
    private String problem;
    private String timestamp;
    
    public CoolerComplaint(String hostel, String floor, String coolerNumber, String problem, String timestamp) {
        this.hostel = hostel;
        this.floor = floor;
        this.coolerNumber = coolerNumber;
        this.problem = problem;
        this.timestamp = timestamp;
    }
    
    public String getHostel() { return hostel; }
    public String getFloor() { return floor; }
    public String getCoolerNumber() { return coolerNumber; }
    public String getProblem() { return problem; }
    public String getTimestamp() { return timestamp; }
}

