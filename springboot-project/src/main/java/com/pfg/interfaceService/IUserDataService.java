package com.pfg.interfaceService;

import java.util.List;

import com.pfg.models.UserData;
import com.pfg.models.Interest;

public interface IUserDataService {

    public void saveUserPreferences(Long userID, Long intID1, Long intID2, Long intID3, Long intID4, Long intID5);

    public List<Long>getInterestList(Long userID);

}
