package com.camelsoft.scano.client.Interfaces;

import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.tools.Enum.Auth.Gender;

import java.util.Date;
import java.util.Set;

public interface CusomUserResponse {
    String getLastname();
    Boolean getActive();
    String getFirstname();
    File_model getImage();
    String getPhonenumber();
    String getPosition();
    String getWebsite();
    String getCompany_name();
    String getCardemail();
    String getArea();
    String getStreet();
    String getBuilding();
    String getFloor();
    String getUnitnumberaddress();
    String getNickname();
    String getEmail();
    String getLandline();
    Gender getGender();
    Date getTimestmp();

    String getAddress();
    File_model getCouverture();
    Set<File_model> getFiles();
    Set<socialmedia> getSocialmedias();
}
