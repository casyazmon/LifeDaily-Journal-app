package com.example.kasi.lifedaily.edit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.kasi.lifedaily.R;
import com.example.kasi.lifedaily.db.AppDatabase;
import com.example.kasi.lifedaily.db.entity.Dairy;

import com.example.kasi.lifedaily.utils.Constants;
import com.example.kasi.lifedaily.utils.Util;

import java.util.Date;



public class EditActivity extends AppCompatActivity implements EditContract.View, EditContract.DateListener {

    private EditContract.Presenter mPresenter;

    private EditText mNameEditText;
    private EditText mAddressEditText;
    //private EditText mEmailEditText;
    private EditText mBirthdayEditText;
    //private EditText mPhoneEditText;

    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mAddressInputLayout;
    //private TextInputLayout mEmailInputLayout;
    private TextInputLayout mBirthdayInputLayout;
    //private TextInputLayout mPhoneTextInputLayout;

    private FloatingActionButton mFab;

    private Dairy dairy;
    private boolean mEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dairy = new Dairy();
        checkMode();

        AppDatabase db = AppDatabase.getDatabase(getApplication());
        mPresenter = new EditPresenter(this, db.dairyModel());

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mEditMode) {
            mPresenter.getDairyAndPopulate(dairy.id);
        }
    }

    private void checkMode() {
        if (getIntent().getExtras() != null) {
            dairy.id = getIntent().getLongExtra(Constants.Dairy_ID, 0);
            mEditMode = true;
        }
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mAddressEditText = (EditText) findViewById(R.id.addressEditText);
       // mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mBirthdayEditText = (EditText) findViewById(R.id.birthdayEditText);
        //mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);

        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        mAddressInputLayout = (TextInputLayout) findViewById(R.id.addressTextInputLayout);
       // mEmailInputLayout = (TextInputLayout) findViewById(R.id.emailTextInputLayout);
        mBirthdayInputLayout = (TextInputLayout) findViewById(R.id.birthdayTextInputLayout);
       // mPhoneTextInputLayout = (TextInputLayout) findViewById(R.id.phoneTextInputLayout);

        mBirthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showDateDialog();
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setImageResource(mEditMode ? R.drawable.ic_refresh : R.drawable.ic_done);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dairy.name = mNameEditText.getText().toString();
                dairy.address = mAddressEditText.getText().toString();
                /*dairy.email = mEmailEditText.getText().toString();
                dairy.phone = mPhoneEditText.getText().toString();*/

                boolean valid = mPresenter.validate(dairy);

                if (!valid) return;

                if (mEditMode) {
                    mPresenter.update(dairy);
                } else {
                    mPresenter.save(dairy);
                }
            }
        });
    }

    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorMessage(int field) {
        if (field == Constants.FIELD_NAME) {
            mNameTextInputLayout.setError(getString(R.string.invalid_name));
       /* } else if (field == Constants.FIELD_EMAIL) {
            mEmailInputLayout.setError(getString(R.string.invalid_email));*/
        /*} else if (field == Constants.FIELD_PHONE) {
            mPhoneTextInputLayout.setError(getString(R.string.invalid_phone));*/
        } else if (field == Constants.FIELD_ADDRESS) {
            mAddressInputLayout.setError(getString(R.string.invalid_address));
        } else if (field == Constants.FIELD_BIRTHDAY) {
            mBirthdayInputLayout.setError(getString(R.string.invalid_birthday));
        }
    }

    @Override
    public void clearPreErrors() {
        mNameTextInputLayout.setErrorEnabled(false);
        /*mEmailInputLayout.setErrorEnabled(false);
        mPhoneTextInputLayout.setErrorEnabled(false);*/
        mAddressInputLayout.setErrorEnabled(false);
        mBirthdayInputLayout.setErrorEnabled(false);
    }

    @Override
    public void openDateDialog() {
        DateDialogFragment fragment = new DateDialogFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void populate(Dairy dairy) {
        this.dairy = dairy;
        mNameEditText.setText(dairy.name);
        mAddressEditText.setText(dairy.address);
        //mEmailEditText.setText(dairy.email);
        mBirthdayEditText.setText(Util.format(dairy.birthday));
       // mPhoneEditText.setText(dairy.phone);
    }

    @Override
    public void setSelectedDate(Date date) {
        dairy.birthday = date;
        mBirthdayEditText.setText(Util.format(date));
    }
}
