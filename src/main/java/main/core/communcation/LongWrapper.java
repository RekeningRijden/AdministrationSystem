package main.core.communcation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric
 */
public class LongWrapper {

    private Long value;

    public LongWrapper() {
        //Not used
    }

    public LongWrapper(Long value) {
        this.value = value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static List<LongWrapper> wrapLongs(List<Long> longs) {
        List<LongWrapper> wrapperList = new ArrayList<>();
        for (Long longValue : longs) {
            LongWrapper wrapper = new LongWrapper(longValue);
            wrapperList.add(wrapper);
        }
        return wrapperList;
    }
}