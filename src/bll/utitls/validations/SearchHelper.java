package bll.utitls.validations;

import be.User;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper {
    public List<User> search(List<User> base, String query) {
        List<User> resultList = new ArrayList<>();

        if (query.isEmpty()){
            return base;
        }
        for (User u: base
             ) {
            if(u.getEmail().toLowerCase().contains(query)){
                resultList.add(u);
            }else if(u.getFirstName().toLowerCase().contains(query)){
                resultList.add(u);
            }
        }
        return resultList;
    }
}
