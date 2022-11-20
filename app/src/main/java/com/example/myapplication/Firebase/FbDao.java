package com.example.myapplication.Firebase;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Game;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.Voucher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class FbDao {


    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "Firebase Dao";
    private static List<Game> listGame;
    private static List<User> listUser;
    private static List<Voucher> listVoucher;


    public FirebaseStorage storageFireBase;
    public static StorageReference avatatRef;

    public static List<Game> getListGame() {
        return listGame;
    }

    public static List<Voucher> getListVoucher() {
        return listVoucher;
    }

    public static java.util.List<User> getList() {
        return listUser;
    }

    public static Bitmap Avatar;
    public static User UserLogin;
    public static Activity activity;
    public static boolean Login = false;

    //trả về trạng thái khi load dữ liệu xong thằng nào đụng vào đấm chết
    public static boolean Loaded = false;
    public static boolean LoadedAvatar = false;
    public static boolean UpdatedUser = false;
    public static boolean UpLoadedAvatar = false;

    public FbDao(Activity context) {
        storageFireBase = FirebaseStorage.getInstance();
        avatatRef = storageFireBase.getReference().child("avatar");
        ReadUser();
        ReadVoucher();
        ReadGame();
        activity = context;

    }

    public FbDao() {


    }

    public User getUserLogin() {

        return UserLogin;
    }

    public void UpLoadavatar(ImageView imageView) {
        String id = UserLogin.getId();
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = avatatRef.child(id).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "onFailure: to upload ", null);
                UpLoadedAvatar = true;

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e(TAG, "onSuccess: to upload ", null);
                UpLoadedAvatar = true;

            }
        });

    }

    public static void LoadAvatarFromID() {
        String id = UserLogin.getId();

        StorageReference avartar = avatatRef.child((id));

        final long ONE_MEGABYTE = 1024 * 1024;
        avartar.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.e(TAG, "onSuccess: ", null);
                Avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                UserLogin.setAvatar(Avatar);

                LoadedAvatar = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "onFailure: ", null);
                LoadedAvatar = true;
            }
        });


    }


    public static void Login(String id) {
        DatabaseReference myRef = database.getReference("Users");
        DatabaseReference userRef = myRef.child(id);
        Bitmap avatar = UserLogin.getAvatar();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserLogin = snapshot.getValue(User.class);
                UserLogin.setId(snapshot.getKey());
                UserLogin.setAvatar(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error, null);
            }
        });
    }


    private void ReadGame() {
        listGame = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Game");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listGame.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Game u = dt.getValue(Game.class);
                    if (u == null) {
                        continue;
                    }
                    listGame.add(u);
                }
                Log.d(TAG, "Đã nhận dữ liệu Game: ");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
    }

    private void ReadVoucher() {
        Log.d(TAG, "ReadVoucher: ");
        listVoucher = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Voucher");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listVoucher.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Voucher u = dt.getValue(Voucher.class);
                    if (u == null) {
                        continue;
                    }
                    listVoucher.add(u);
                }
                Log.d(TAG, "Đã nhận dữ liệu voucher: ");


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });
    }

    public void AddUser(User user) {
        DatabaseReference myRef = database.getReference("Users");
        myRef.push().setValue(user);
    }

    public void UpdateUser(User user1) {
        DatabaseReference myRef = database.getReference("Users").child(user1.getId());
        User user = user1;
        user.setAvatar(null);
        user.setId(null);
        myRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                UpdatedUser = true;
            }
        });
    }

    public void ReadUser() {
        Log.d(TAG, "ReadUser: ");
        listUser = new ArrayList<>();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {

                    User u = dt.getValue(User.class);
                    if (u == null) {
                        return;
                    }
                    u.setId(dt.getKey());

                    listUser.add(u);
                }
                Loaded = true;
                Log.d(TAG, "Đã nhận dữ liệu User");
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.toString()
                );
            }
        });


    }

    ///////////////////////////////////////////////////////////////////////////

}
