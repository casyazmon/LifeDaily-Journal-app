package com.example.kasi.lifedaily.listedit;

import com.example.kasi.lifedaily.BasePresenter;
import com.example.kasi.lifedaily.BaseView;
import com.example.kasi.lifedaily.db.entity.Dairy;


import java.util.List;



public interface ListContract {

    interface Presenter extends BasePresenter {

        void addNewDairy();

        void result(int requestCode, int resultCode);

        void populatePeople();

        void openEditScreen(Dairy dairy);

        void openConfirmDeleteDialog(Dairy dairy);

        void delete(long dairyId);
    }

    interface View extends BaseView<Presenter> {

        void showAddDairy();

        void setDairys(List<Dairy> dairys);

        void showEditScreen(long id);

        void showDeleteConfirmDialog(Dairy dairy);

        void showEmptyMessage();
    }

    interface OnItemClickListener {

        void clickItem(Dairy Dairy);

        void clickLongItem(Dairy Dairy);
    }

    interface DeleteListener {

        void setConfirm(boolean confirm, long dairyId);

    }
}
