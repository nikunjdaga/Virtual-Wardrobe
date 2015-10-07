package com.example.nikunj.virtualwardrobe;



import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;


public class PhotosViewPager extends com.blunderer.materialdesignlibrary.activities.ViewPagerActivity {



    @Override
    public ViewPagerHandler getViewPagerHandler() {
        return new ViewPagerHandler(this)
                .addPage("First Image",
                        PhotosViewPagerFragment.newInstance("Material Design ViewPager"))
                .addPage("Second Image",
                        PhotosViewPagerFragment.newInstance("Material Design ViewPager"));
    }

    @Override
    public boolean showViewPagerIndicator() {
        return false;
    }

    @Override
    public boolean replaceActionBarTitleByViewPagerPageTitle() {
        return true;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this) ;
    }



    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }
}
