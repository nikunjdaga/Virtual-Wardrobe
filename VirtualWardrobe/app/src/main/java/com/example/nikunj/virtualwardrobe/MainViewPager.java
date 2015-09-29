package com.example.nikunj.virtualwardrobe;



import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
        import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
        import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
        import com.example.nikunj.virtualwardrobe.MainFragment;
        import com.example.nikunj.virtualwardrobe.R;

public class MainViewPager extends com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity {

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
        return new ActionBarDefaultHandler(this);
    }

}