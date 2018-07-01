package com.example.kasi.lifedaily.edit;

import com.example.kasi.lifedaily.BasePresenter;
import com.example.kasi.lifedaily.BaseView;
import com.example.kasi.lifedaily.db.entity.Dairy;


import java.util.Date;



public interface EditContract {

    interface Presenter extends BasePresenter {
        void save(Dairy dairy);

        boolean validate(Dairy dairy);

        void showDateDialog();

        void getDairyAndPopulate(long id);

        void update(Dairy dairy);
    }

    interface View extends BaseView<Presenter> {

        void showErrorMessage(int field);

        void clearPreErrors();

        void openDateDialog();

        void close();

        void populate(Dairy dairy);
    }

    interface DateListener {

        void setSelectedDate(Date date);

    }
}
