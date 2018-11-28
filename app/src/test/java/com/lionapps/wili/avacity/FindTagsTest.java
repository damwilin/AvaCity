package com.lionapps.wili.avacity;

import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.utils.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FindTagsTest {
    @Test
    public void testTag() {
        Place place = new Place();
        List<String> placeTags = new ArrayList();
        placeTags.add("aaa ");
        placeTags.add("bbb");
        placeTags.add("ccc");
        place.setTags(placeTags);
        String queryTags = "aaa ";
        assertEquals(true, Utils.isPlaceContainsTags(place, queryTags));
    }
}
