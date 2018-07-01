package com.example.kasi.lifedaily.listedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kasi.lifedaily.R;
import com.example.kasi.lifedaily.db.AppDatabase;
import com.example.kasi.lifedaily.db.entity.Dairy;
import com.example.kasi.lifedaily.edit.EditActivity;

import com.example.kasi.lifedaily.utils.Constants;

import java.util.List;



public class ListActivity extends AppCompatActivity implements ListContract.View, ListContract.OnItemClickListener, ListContract.DeleteListener {

    private ListContract.Presenter mPresenter;
    private PeopleAdapter mPeopleAdapter;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPeopleAdapter = new PeopleAdapter(this);
        recyclerView.setAdapter(mPeopleAdapter);

        AppDatabase db = AppDatabase.getDatabase(getApplication());
        mPresenter = new ListPresenter(this, db.dairyModel());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.populatePeople();
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
