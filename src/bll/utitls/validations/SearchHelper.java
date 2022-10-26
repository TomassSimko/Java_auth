package bll.utitls.validations;

import be.User;
import java.util.List;
public class SearchHelper {
    public List<User> search(List<User> base, String query) {
        return !query.isEmpty() ?
                base.stream().filter((user) ->
                        user.getEmail().toLowerCase().contains(query)).toList()
                : base;
    }
}
