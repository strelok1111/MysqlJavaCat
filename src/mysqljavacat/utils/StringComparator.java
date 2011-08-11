package mysqljavacat.utils;

import java.util.Comparator;

/**
 *
 * @author strelok
 */
public class StringComparator implements Comparator {
     public int compare(Object obj, Object obj1) {
         return obj.toString().compareTo(obj1.toString());
     }
}