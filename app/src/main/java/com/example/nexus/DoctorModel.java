package com.example.nexus;

public class DoctorModel {
    private String FullName;
    private String Speciality;
    private String ConsultationFee;
    private String HospitalClinicAddress;
    private String Experience;
    private String Education;
    private String PhoneNumber;

    public DoctorModel(){}
    public DoctorModel(String FullName,String Speciality,String ConsultationFee,String HospitalClinicAddress, String Experience,String Education,String PhoneNumber   ){
        this.FullName=FullName;
        this.Speciality=Speciality;
        this.ConsultationFee=ConsultationFee;
        this.HospitalClinicAddress=HospitalClinicAddress;
        this.Education=Education;
        this.Experience=Experience;
        this.PhoneNumber=PhoneNumber;
    }


    public String getFullName() {
        return FullName;
    }
    public String getSpeciality() {
        return Speciality;
    }


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEducation() {
        return Education;
    }

    public String getExperience() {
        return Experience;
    }

    public String getHospitalClinicAddress() {
        return HospitalClinicAddress;
    }

    public String getConsultationFee() {
        return ConsultationFee;
    }
}
