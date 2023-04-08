package com.donghh.studentmanagement;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.donghh.studentmanagement.database.DatabaseHandler;
import com.donghh.studentmanagement.entity.CategoryRoom;
import com.donghh.studentmanagement.entity.Department;
import com.donghh.studentmanagement.entity.Payment;
import com.donghh.studentmanagement.entity.Room;
import com.donghh.studentmanagement.entity.Student;
import com.donghh.studentmanagement.setting.SettingFragment;
import com.donghh.studentmanagement.ui.ViewPagerAdapter;
import com.donghh.studentmanagement.ui.notifications.NotificationsFragment;
import com.donghh.studentmanagement.ui.payment.PaymentFragment;
import com.donghh.studentmanagement.ui.room.RoomFragment;
import com.donghh.studentmanagement.ui.student.StudentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.donghh.studentmanagement.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHandler databaseHandler;
    private static final int MENU_ITEM_ID_ONE =1;
    private static final int MENU_ITEM_ID_TWO =2;
    private static final int MENU_ITEM_ID_THREE =3;
    private static final int MENU_ITEM_ID_FOUR =4;
    private static final int MENU_ITEM_ID_FIVE =5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null,DatabaseHandler.DATABASE_VERSION);

        if (!Common.getBoolean(MainActivity.this, Common.CREATE_DATABASE)) {
            addDatabase();
            Common.putBoolean(MainActivity.this, Common.CREATE_DATABASE, true);
        }


//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.vpData.setCurrentItem(item.getItemId()-1);

                return true;
            }

        });
        Menu menu = binding.navView.getMenu();
        ArrayList<Fragment> listFragment = new ArrayList<>();
        if (Common.getBoolean(MainActivity.this, Common.IS_MANAGER)) {
            listFragment.add(StudentFragment.newInstance());
            listFragment.add(RoomFragment.newInstance());
            listFragment.add(PaymentFragment.newInstance());
            listFragment.add(NotificationsFragment.newInstance());
            listFragment.add(SettingFragment.newInstance());

            menu.add(Menu.NONE, MENU_ITEM_ID_ONE, Menu.NONE, getString(R.string.student))
                    .setIcon(R.drawable.ic_home_black_24dp);
            menu.add(Menu.NONE, MENU_ITEM_ID_TWO, Menu.NONE, getString(R.string.room))
                    .setIcon(R.drawable.ic_baseline_bedroom_child_24);
            menu.add(Menu.NONE, MENU_ITEM_ID_THREE, Menu.NONE, getString(R.string.payment))
                    .setIcon(R.drawable.ic_dashboard_black_24dp);
            menu.add(Menu.NONE, MENU_ITEM_ID_FOUR, Menu.NONE, getString(R.string.title_notifications))
                    .setIcon(R.drawable.ic_notifications_black_24dp);
            menu.add(Menu.NONE, MENU_ITEM_ID_FIVE, Menu.NONE, getString(R.string.setting))
                    .setIcon(R.drawable.ic_baseline_settings_24);

        }else {
            listFragment.add(StudentFragment.newInstance());
            listFragment.add(SettingFragment.newInstance());
            menu.add(Menu.NONE, MENU_ITEM_ID_ONE, Menu.NONE, getString(R.string.student))
                    .setIcon(R.drawable.ic_home_black_24dp);
            menu.add(Menu.NONE, MENU_ITEM_ID_FIVE, Menu.NONE, getString(R.string.setting))
                    .setIcon(R.drawable.ic_baseline_settings_24);
        }

        ViewPagerAdapter adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        binding.vpData.setOffscreenPageLimit(listFragment.size());
        binding.vpData.setAdapter(adapterViewPager);

    }

    private void addDatabase() {
        try{
            databaseHandler.addStudent(new Student("ST001","Hoang Hai Dong","27/07/1994","0986654794",0,"Hà Nội","KH_CNTT","101"));
            //khoa
            databaseHandler.addDepartment(new Department("KH_CNTT","Công nghệ thông tin"));
            databaseHandler.addDepartment(new Department("KH_DTVT","Điện tử viễn thông"));

            //loại phòng
            databaseHandler.addCategoryRoom(new CategoryRoom("VIP1","Loại 1",10,200000));
            databaseHandler.addCategoryRoom(new CategoryRoom("VIP2","Loại 2",8,500000));
            databaseHandler.addCategoryRoom(new CategoryRoom("VIP3","Loại 3",6,1000000));

            //Phòng
            databaseHandler.addRoom(new Room("101","Phòng 101","VIP1"));
            databaseHandler.addRoom(new Room("102","Phòng 102","VIP1"));
            databaseHandler.addRoom(new Room("103","Phòng 103","VIP1"));
            databaseHandler.addRoom(new Room("104","Phòng 104","VIP1"));

            databaseHandler.addRoom(new Room("201","Phòng 201","VIP2"));
            databaseHandler.addRoom(new Room("202","Phòng 202","VIP2"));
            databaseHandler.addRoom(new Room("203","Phòng 203","VIP2"));
            databaseHandler.addRoom(new Room("204","Phòng 204","VIP2"));

            databaseHandler.addRoom(new Room("301","Phòng 301","VIP3"));
            databaseHandler.addRoom(new Room("302","Phòng 302","VIP3"));
            databaseHandler.addRoom(new Room("303","Phòng 303","VIP3"));
            databaseHandler.addRoom(new Room("304","Phòng 304","VIP3"));

        }catch(Exception e){
          e.printStackTrace();
        }
    }

}