package Request.Analytics;

import java.util.List;

/**
 * Created by alco on 28/07/2015.
 * Analytics contain math test for request class
 */
public class Analytics {

    public Analytics() {
    }

    /**
     * tryOccurrenceExist test if an occurrence exist!
     *
     * @param list
     * @param table
     * @return
     */
    public Boolean tryOccurrenceExist(List<String> list, String table) {
        Boolean bool = false;

        for (String found : list) {
            if (table.equals(found)) {
                bool = true;
            }
        }

        return bool;
    }

    /**
     * tryOccurrenceNotExist test if an occurrence not exist!
     *
     * @param list
     * @param table
     * @return
     */
    public Boolean tryOccurrenceNotExist(List<String> list, String table) {
        Boolean bool = true;

        for (String found : list) {
            if (table.equals(found)) {
                bool = false;
            }
        }

        return bool;
    }
}
