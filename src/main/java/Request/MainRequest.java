package Request;

import java.util.List;

/**
 * Created by alco on 28/07/2015.
 */
public interface MainRequest {
    List<String> createAllRequest(List<String> typeListSort, List<String> typeList, List<String> tableList, List<String> champsList, List<String> conditionList)
            throws IllegalArgumentException;
}