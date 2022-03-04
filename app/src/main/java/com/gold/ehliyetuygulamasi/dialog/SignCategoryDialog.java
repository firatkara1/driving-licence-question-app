package com.gold.ehliyetuygulamasi.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.adapter.SignCategoryAdapter;
import com.gold.ehliyetuygulamasi.databinding.DialogBinding;
import com.gold.ehliyetuygulamasi.databinding.ActivityMainBinding;
import com.gold.ehliyetuygulamasi.model.SignCategoryModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class SignCategoryDialog extends DialogFragment implements SignCategoryAdapter.OnSignCategoryItemClicked {

    Button dialog_sign_category_close_btn;

    TextView txtTitle;
    FirebaseFirestore database;

    public SignCategoryDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        final ArrayList<SignCategoryModel> signCategories = new ArrayList<>();
        final SignCategoryAdapter adapter = new SignCategoryAdapter(getContext(), signCategories);

        database = FirebaseFirestore.getInstance();

        RecyclerView signCategoryListRcy = view.findViewById(R.id.categoryRecyclerView);

        signCategoryListRcy.setLayoutManager(new GridLayoutManager(getContext(),1));
        signCategoryListRcy.setHasFixedSize(true);
        signCategoryListRcy.setAdapter(adapter);

        dialog_sign_category_close_btn = view.findViewById(R.id.dialog_sign_category_close_btn);
        txtTitle = view.findViewById(R.id.dialog_signcategory_title);
        txtTitle.setText("Trafik Levhaları");

        dialog_sign_category_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requireActivity().getViewModelStore().clear();
                Objects.requireNonNull(getDialog()).dismiss();

            }
        });


        database.collection("sign_category")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        signCategories.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {

                            SignCategoryModel model = snapshot.toObject(SignCategoryModel.class);
                            assert model != null;
                            model.setSignCategoryId(snapshot.getId());
                            signCategories.add(model);

                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        super.onViewCreated(view, savedInstanceState);

    }

}






















