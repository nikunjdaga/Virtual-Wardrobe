package com.example.nikunj.virtualwardrobe;



import android.widget.Toast;

import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
        import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.blunderer.materialdesignlibrary.listeners.OnSearchDynamicListener;
import com.blunderer.materialdesignlibrary.listeners.OnSearchListener;
import com.blunderer.materialdesignlibrary.views.ToolbarSearch;
import com.example.nikunj.virtualwardrobe.MainFragment;
        import com.example.nikunj.virtualwardrobe.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainViewPager extends com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity {


    private final static List<String> mItems = new ArrayList<>(
            Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                    "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
                    "seventeen", "eighteen", "nineteen", "twenty","twenty two"));

    @Override
    public ViewPagerHandler getViewPagerHandler() {

        return new ViewPagerHandler(this)
                .addPage("Types", new TypeFragment())
                .addPage("Favourites", new FavouritesFragment())
                .addPage("Colors", new ColorFragment())
                .addPage("Collections", new CollectionsFragment());
    }

    @Override
    public boolean expandTabs() {
        return false;
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarSearchHandler(this, new OnSearchListener() {

            @Override
            public void onSearched(String text) {
                Toast.makeText(getApplicationContext(),
                        "Searching \"" + text + "\"", Toast.LENGTH_SHORT).show();
            }

        })
                .enableAutoCompletion()
                .setAutoCompletionSuggestions(mItems)
              //  .setAutoCompletionItems(mItems)
                .setAutoCompletionMode(ToolbarSearch.AutoCompletionMode.CONTAINS);
    }

}

