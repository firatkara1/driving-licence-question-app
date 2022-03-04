package com.gold.ehliyetuygulamasi.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gold.ehliyetuygulamasi.R;
import com.gold.ehliyetuygulamasi.adapter.CategoryAdapter;
import com.gold.ehliyetuygulamasi.adapter.ImageQuestionCategoryAdapter;
import com.gold.ehliyetuygulamasi.model.CategoryModel;
import com.gold.ehliyetuygulamasi.model.ImageQuestionCategoryModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

public class ImageQuestionCategoryDialog extends DialogFragment implements ImageQuestionCategoryAdapter.OnQuestionImageCategoryItemClicked {

    Button dialog_category_close_btn;
    TextView txtTitle;

    FirebaseFirestore database;

    public  ImageQuestionCategoryDialog(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog,container,false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        final ArrayList<ImageQuestionCategoryModel> models = new ArrayList<>();
        final ImageQuestionCategoryAdapter adapter = new ImageQuestionCategoryAdapter(getContext(),models);
        database = FirebaseFirestore.getInstance();

        RecyclerView questionCategoryListRcy = view.findViewById(R.id.categoryRecyclerView);
        questionCategoryListRcy.setLayoutManager(new GridLayoutManager(getContext(),1));
        questionCategoryListRcy.setHasFixedSize(true);
        questionCategoryListRcy.setAdapter(adapter);

        dialog_category_close_btn = view.findViewById(R.id.dialog_sign_category_close_btn);
        txtTitle = view.findViewById(R.id.dialog_signcategory_title);
        txtTitle.setText("Test Seçin");

        dialog_category_close_btn.setOnClickListener(v -> {
            requireActivity().getViewModelStore().clear();
            Objects.requireNonNull(getDialog()).dismiss();
        });

        database.collection("image_question_categories").orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    models.clear();
                    assert value != null;
                    for (DocumentSnapshot snapshot : value.getDocuments()){

                        ImageQuestionCategoryModel model = snapshot.toObject(ImageQuestionCategoryModel.class);
                        assert model != null;
                        model.setCategoryId(snapshot.getId());
                        models.add(model);
                    }
                    adapter.notifyDataSetChanged();
                });

        super.onViewCreated(view, savedInstanceState);
    }
}































