package com.example.kasi.lifedaily.listedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kasi.lifedaily.R;
import com.example.kasi.lifedaily.db.AppDatabase;
import com.example.kasi.lifedaily.db.entity.Dairy;
import com.example.kasi.lifedaily.edit.EditActivity;

import com.example.kasi.lifedaily.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;



public class ListActivity extends AppCompatActivity implements ListContract.View, ListContract.OnItemClickListener, ListContract.DeleteListener {

    private ListContract.Presenter mPresenter;
    private PeopleAdapter mPeopleAdapter;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewDairy();
            }
        });

        mEmptyTextView = (TextView) findViewById(R.id.emptyTextView);
        mAuth = FirebaseAuth.getInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPeopleAdapter = new PeopleAdapter(this);
        recyclerView.setAdapter(mPeopleAdapter);

        AppDatabase db = AppDatabase.getDatabase(getApplication());
        mPresenter = new ListPresenter(this, db.dairyModel());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout) {
            /*mDrawerLayout.openDrawer(GravityCompat.START);*/
            mAuth.signOut();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.populatePeople();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void showAddDairy() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    public void setDairys(List<Dairy> dairys) {
        mEmptyTextView.setVisibility(View.GONE);
        mPeopleAdapter.setValues(dairys);
    }

    @Override
    public void showEditScreen(long id) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(Constants.Dairy_ID, id);
        startActivity(intent);
    }

    @Override
    public void showDeleteConfirmDialog(Dairy dairy) {
        DeleteConfirmFragment fragment = new DeleteConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.Dairy_ID, dairy.id);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "confirmDialog");
    }

    @Override
    public void showEmptyMessage() {
        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void clickItem(Dairy dairy) {
        mPresenter.openEditScreen(dairy);
    }

    @Override
    public void clickLongItem(Dairy dairy) {
        mPresenter.openConfirmDeleteDialog(dairy);
    }

    @Override
    public void setConfirm(boolean confirm, long DairyId) {
        if (confirm) {
            mPresenter.delete(DairyId);
        }
    }
}
