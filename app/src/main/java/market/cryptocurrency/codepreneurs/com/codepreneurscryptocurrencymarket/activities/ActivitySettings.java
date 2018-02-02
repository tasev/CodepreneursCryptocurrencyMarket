package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.MainActivity;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.base.BaseActivity;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.login.LoginActivity;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.menagers.PreferencesManager;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.User;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilSettings;

import static market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants.CNY;
import static market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants.EUR;

/**
 * Created by tasev on 12/9/17.
 */

public class ActivitySettings extends BaseActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = ActivitySettings.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.radiButtonUSD)
    RadioButton radiButtonUSD;

    @BindView(R.id.radiButtonEUR)
    RadioButton radiButtonEUR;

    @BindView(R.id.radiButtonCUD)
    RadioButton radiButtonCUD;

    @BindView(R.id.txt_user)
    TextView txtDetails;

    @BindView(R.id.btn_logout)
    Button logoutButton;

    int[] limitList;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseActivity();
        initiateViews();
        initiateUserUI();
    }

    private void initiateUserUI() {
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Crypto Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);
                Log.e(TAG, "App title -> " + appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        userId = PreferencesManager.getUserID(this);
        if (!TextUtils.isEmpty(userId)) {
            addUserChangeListener();
        }

        toggleButton();
        // Save / update the user
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userId)) {
                    startActivity(new Intent(ActivitySettings.this, LoginActivity.class));
                    finish();
                } else {
                    PreferencesManager.removeUser(ActivitySettings.this);
                    PreferencesManager.removeUserID(ActivitySettings.this);
                    initiateUserUI();
                }
            }
        });
    }

    // Changing button text
    private void toggleButton() {
        if (!TextUtils.isEmpty(userId)) {
            logoutButton.setText("Log Out");
        } else {
            logoutButton.setText("Log In");
            txtDetails.setText("");
        }
    }

    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);

                // Display newly updated name and email
                txtDetails.setText(user.name + ",\n " + user.email);

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_done:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        UtilSettings.getInstance().setCurrentconvertLimit(limitList[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            UtilSettings.getInstance().setCurrentconvertVal(buttonView.getText().toString());
    }

    private void initiateViews() {
        String currentConvertVal = UtilSettings.getInstance().getCurrentconvertVal();
        int currentconvertLimit = UtilSettings.getInstance().getCurrentconvertLimit();
        switch (currentConvertVal) {
            case EUR:
                radiButtonEUR.setChecked(true);
                break;
            case CNY:
                radiButtonCUD.setChecked(true);
                break;
            default:
                radiButtonUSD.setChecked(true);
        }
        limitList = getResources().getIntArray(R.array.limit_array_int);
        for (int i = 0; i < limitList.length; i++) {
            if (currentconvertLimit == limitList[i]) {
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setOnItemSelectedListener(this);
        radiButtonCUD.setOnCheckedChangeListener(this);
        radiButtonEUR.setOnCheckedChangeListener(this);
        radiButtonUSD.setOnCheckedChangeListener(this);
    }

    private void initialiseActivity() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
    }


}
