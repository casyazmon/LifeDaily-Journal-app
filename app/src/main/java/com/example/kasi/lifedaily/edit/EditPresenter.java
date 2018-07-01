package com.example.kasi.lifedaily.edit;



import com.example.kasi.lifedaily.db.dao.DairyDao;

import com.example.kasi.lifedaily.db.entity.Dairy;
import com.example.kasi.lifedaily.utils.Constants;
import com.example.kasi.lifedaily.utils.Util;

public class EditPresenter implements EditContract.Presenter {

    private final EditContract.View mView;
    private final DairyDao dairyDao;

    public EditPresenter(EditContract.View mMainView, DairyDao dairyDao) {
        this.mView = mMainView;
        this.mView.setPresenter(this);
        this.dairyDao = dairyDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void save(Dairy dairy) {
        long ids = this.dairyDao.insertDairy(dairy);
        mView.close();
    }

    @Override
    public boolean validate(Dairy dairy) {
        mView.clearPreErrors();
        if (dairy.name.isEmpty() || !Util.isValidName(dairy.name)) {
            mView.showErrorMessage(Constants.FIELD_NAME);
            return false;
        }
        if (dairy.address.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_ADDRESS);
            return false;
        }

        if (dairy.birthday == null) {
            mView.showErrorMessage(Constants.FIELD_BIRTHDAY);
            return false;
        }

        return true;
    }

    @Override
    public void showDateDialog() {
        mView.openDateDialog();
    }

    @Override
    public void getDairyAndPopulate(long id) {
        Dairy dairy = dairyDao.findDairy(id);
        if (dairy != null) {
            mView.populate(dairy);
        }
    }

    @Override
    public void update(Dairy dairy) {
        int ids = this.dairyDao.updateDairy(dairy);
        mView.close();
    }
}
