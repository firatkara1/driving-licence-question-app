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
import com.gold.ehliyetuygulamasi.adapter.NoteCategoryAdapter;
import com.gold.ehliyetuygulamasi.model.CategoryModel;
import com.gold.ehliyetuygulamasi.model.NoteCategoryModel;
import com.gold.ehliyetuygulamasi.model.NoteModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class NoteCategoryDialog extends DialogFragment implements NoteCategoryAdapter.OnNoteCategoryItemClicked{

    Button dialog_category_close_btn;
    TextView txtTitle;


    FirebaseFirestore database;

    public NoteCategoryDialog(){

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

        final ArrayList<NoteCategoryModel> noteCategories = new ArrayList<>();
        final NoteCategoryAdapter adapter = new NoteCategoryAdapter(getContext(),noteCategories);
        database = FirebaseFirestore.getInstance();

        RecyclerView noteCategoryListRcy = view.findViewById(R.id.categoryRecyclerView);
        noteCategoryListRcy.setLayoutManager(new GridLayoutManager(getContext(),1));
        noteCategoryListRcy.setHasFixedSize(true);
        noteCategoryListRcy.setAdapter(adapter);

        dialog_category_close_btn = view.findViewById(R.id.dialog_sign_category_close_btn);
        txtTitle = view.findViewById(R.id.dialog_signcategory_title);
        txtTitle.setText("Çalışmak İstediğiniz Ders");

        dialog_category_close_btn.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "Kapatildi", Toast.LENGTH_SHORT).show();
            requireActivity().getViewModelStore().clear();
            Objects.requireNonNull(getDialog()).dismiss();
        });

        //String signCatId = getIntent().getStringExtra("signCatId");


        database.collection("notes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                noteCategories.clear();
                assert value != null;
                for (DocumentSnapshot snapshot : value.getDocuments()){

                    NoteCategoryModel model = snapshot.toObject(NoteCategoryModel.class);
                    assert model != null;
                    model.setNoteCategoryId(snapshot.getId());
                    noteCategories.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}





















