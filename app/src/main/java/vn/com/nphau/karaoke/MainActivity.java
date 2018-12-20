package vn.com.nphau.karaoke;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.com.nphau.adapter.SongsAdapter;
import vn.com.nphau.model.Song;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    // databases path
    private static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    // Declaring all controls ...
    private ListView lvAllSongs, lvAllFavoriteSongs;
    // Declaring all adapter ...
    private SongsAdapter adapterAllSongs, adapterAllFavoriteSongs;
    // Declaring all data ...
    private ArrayList<Song> allSongs, allFavoriteSongs;
    // SQLite database
    private String DATABASE_NAME = "karaokevietnam.sqlite";


    // Action bar
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing action bar
        initActionBar();

        // Initializing Tab Host
        setupTabs();

        // Copying databases from assets folder
        processCopy();

        // Initializing all controls
        initControls();

        // Initializing all event of controls
        initEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                return true;
            case R.id.action_refresh:
                // refresh

                return true;
            case R.id.action_help:
                // help action
                return true;
            case R.id.action_check_updates:
                // check for updates action
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initActionBar() {
        // get action bar
        actionBar = getActionBar();

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);


        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#34495e")));

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        return false;
    }

    private void initControls() {
        initALLSongsListView();
        initALLFavoriteSongsListView();

    }

    private void initEvents() {

    }

    private void initALLSongsListView() {
        lvAllSongs = (ListView) findViewById(R.id.lv_all_songs);
        allSongs = new ArrayList<>();
        adapterAllSongs = new SongsAdapter(MainActivity.this, allSongs);
        adapterAllSongs.selectedTab = 0;
        lvAllSongs.setAdapter(adapterAllSongs);
    }

    private void initALLFavoriteSongsListView() {
        lvAllFavoriteSongs = (ListView) findViewById(R.id.lv_favorite_songs);
        allFavoriteSongs = new ArrayList<>();
        adapterAllFavoriteSongs = new SongsAdapter(MainActivity.this, allFavoriteSongs);
        adapterAllFavoriteSongs.selectedTab = 1;
        lvAllFavoriteSongs.setAdapter(adapterAllFavoriteSongs);
    }


    /**
     * Initializing Tab Host for screen
     */
    private void setupTabs() {

        // First: Get Tab host id (built - in android)
        final TabHost tab = (TabHost) findViewById(R.id.tabHost);

        // Call setup
        tab.setup();
        TabHost.TabSpec spec;

        //  Get resources
        Resources resources = getResources();

        //Tạo tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tabAllSongs);
        spec.setIndicator("", resources.getDrawable(R.drawable.allmusic));
        tab.addTab(spec);

        //Tạo tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tabFavoriteSongs);
        spec.setIndicator("", resources.getDrawable(R.drawable.lovemusic));
        tab.addTab(spec);

        tab.setCurrentTab(0);

        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t1")) {
                    showAllBaiHat();
                } else {
                    xuLyHienThiDanhSachRuot();
                }
            }
        });
    }

    /**
     *
     */
    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                copyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     *
     */
    private String getDatabasePath() {

        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    /**
     *
     */
    public void copyDataBaseFromAsset() {
        try {
            InputStream myInput = null;

            // Open file data stream
            myInput = getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = getDatabasePath();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close all streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllBaiHat() {
        database = openOrCreateDatabase(
                DATABASE_NAME,
                MODE_PRIVATE,
                null);
        //
        Cursor cursor = database.query("song",
                null, "language=?", new String[]{"vn"}, null, null, null);
        allSongs.clear();
        while (cursor.moveToNext()) {
            Song song = new Song();

            song.setId(cursor.getString(0));
            song.setName(cursor.getString(1));
            song.setInfo(cursor.getString(5));
            song.setAuthor(cursor.getString(6));
            song.setIsFavorite(cursor.getInt(8));

            allSongs.add(song);
        }
        cursor.close();
        adapterAllFavoriteSongs.notifyDataSetChanged();
    }

    private void xuLyHienThiDanhSachRuot() {
        database = openOrCreateDatabase(
                DATABASE_NAME,
                MODE_PRIVATE,
                null);
        //
        Cursor cursor = database.query("song",
                null,
                "isFavorite=?", new String[]{"1"},
                null, null, null);

        allFavoriteSongs.clear();

        while (cursor.moveToNext()) {
            Song song = new Song();

            song.setId(cursor.getString(0));
            song.setName(cursor.getString(1));
            song.setInfo(cursor.getString(5));
            song.setAuthor(cursor.getString(6));
            song.setIsFavorite(cursor.getInt(8));

            allFavoriteSongs.add(song);
        }

        cursor.close();

        adapterAllSongs.notifyDataSetChanged();
    }


}
