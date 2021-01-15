package com.example.habiticasmaextension.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.GroupMember;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.services.PreferencesServiceFactory;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    public User user;
    public List<GroupMember> groupMembers;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerView;
    NavController navController;
    TextView textTitle;
    TextView textUsername;
    TextView textClass;
    TextView textLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra("user");

        drawerLayout = findViewById(R.id.drawer_layout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = findViewById(R.id.navigation_view);
        navigationView.bringToFront();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        textTitle = findViewById(R.id.title_text);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });

        headerView = navigationView.getHeaderView(0);

        textUsername = (TextView) headerView.findViewById(R.id.user_username);
        textUsername.setText(user.username);

        textClass = (TextView) headerView.findViewById(R.id.user_class);
        textClass.setText(user.stats.klass);

        textLevel = (TextView) headerView.findViewById(R.id.user_level);
        textLevel.setText("Level " + user.stats.level);
    }

    public void performLogout(MenuItem item) {
        PreferencesServiceFactory.createService().remove("apiKey", getApplicationContext());
        PreferencesServiceFactory.createService().remove("userId", getApplicationContext());

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("loggingOut", true);
        MainActivity.this.startActivity(intent);

        setResult(Activity.RESULT_OK);
        finish();
    }
}