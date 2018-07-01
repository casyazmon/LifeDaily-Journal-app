package com.example.kasi.lifedaily.listedit;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.kasi.lifedaily.db.dao.DairyDao;
import com.example.kasi.lifedaily.db.entity.Dairy;


import java.util.List;



public class ListPresenter implements ListContract.Presenter {

    private final ListContract.View mView;
    private final DairyDao dairyDao;

    public ListPresenter(ListContract.View view, DairyDao dairyDao) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.dairyDao = dairyDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void addNewDairy() {
        mView.showAddDairy();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void populatePeople() {
        dairyDao.findAllDairys().observeForever(new Observer<List<Dairy>>() {
            @Override
            public void onChanged(@Nullable List<Dairy> Dairys) {
                mView.setDairys(Dairys);
                if (Dairys == null || Dairys.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void openEditScreen(Dairy Dairy) {
        mView.showEditScreen(Dairy.id);
    }

    @Override
    public void openConfirmDeleteDialog(Dairy Dairy) {
        mView.showDeleteConfirmDialog(Dairy);
    }

    @Override
    public void delete(long DairyId) {
        Dairy dairy = dairyDao.findDairy(DairyId);
        dairyDao.deleteDairy(dairy);
    }
}
