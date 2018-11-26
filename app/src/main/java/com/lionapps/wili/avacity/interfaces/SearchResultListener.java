package com.lionapps.wili.avacity.interfaces;

import com.lionapps.wili.avacity.models.Place;

import java.util.List;

public interface SearchResultListener {
    void successGettingSearchData(List<Place> searchList);
}
