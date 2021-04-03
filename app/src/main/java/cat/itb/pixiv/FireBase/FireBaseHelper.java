package cat.itb.pixiv.FireBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import cat.itb.pixiv.ClassesModels.IllustrationClass;
import cat.itb.pixiv.ClassesModels.MangaClass;
import cat.itb.pixiv.ClassesModels.NovelClass;
import cat.itb.pixiv.ClassesModels.User;
import id.zelory.compressor.Compressor;

public class FireBaseHelper {

    private static FirebaseDatabase database;

    private static DatabaseReference referenceIllustrationsRecommended;
    private static DatabaseReference referenceIllustrationsRanking;
    private static DatabaseReference referenceIllustrationsPopularLives;
    private static DatabaseReference referenceMangaRecommended;
    private static DatabaseReference referenceMangaRanking;
    private static DatabaseReference referenceMangaPixivVision;
    private static DatabaseReference referenceNovelsRecommended;
    private static DatabaseReference referenceNovelsRanking;

    private static String keyU;

    public static  String urlImage;

    private static DatabaseReference referenceUsers;
    private static DatabaseReference referenceImage;

    private static DatabaseReference referenceImageUser;
    private static String defaultImage = "";

    private static StorageReference storageImageReference;




    private static DatabaseReference userMyWorksIllustrations;
    private static DatabaseReference userMyWorksManga;
    private static DatabaseReference userMyWorksNovels;

    private static DatabaseReference userMyWorksPublicIllustrations;
    private static DatabaseReference userMyWorksPublicManga;
    private static DatabaseReference userMyWorksPublicNovels;

    private static DatabaseReference userCollectionsIllustrations;
    private static DatabaseReference userCollectionsManga;
    private static DatabaseReference userCollectionsNovels;

    private static DatabaseReference userMyWorks;

    public static void setFirstsReferneces(){
        database = FirebaseDatabase.getInstance();
        referenceUsers = database.getReference("Users");
        referenceImage = database.getReference("Image");
    }

    public static void setAllReferences(){
        storageImageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");


        referenceIllustrationsRecommended  =  referenceImage.child("IllustrationsRecommended");
        referenceIllustrationsRanking = referenceImage.child("IllustrationRanking");
        referenceIllustrationsPopularLives = referenceImage.child("IllustrationPopularLives");
        referenceMangaRecommended = referenceImage.child("MangaRecommended");
        referenceMangaRanking = referenceImage.child("MangaRanking");
        referenceMangaPixivVision = referenceImage.child("PixivVision");
        referenceNovelsRecommended = referenceImage.child("NovelsRecommended");
        referenceNovelsRanking = referenceImage.child("NovelsRanking");


        userMyWorks = referenceUsers.child(keyU).child("MyWorks");
        userMyWorksIllustrations= userMyWorks.child("Illustration");
        userMyWorksManga= userMyWorks.child("Manga");
        userMyWorksNovels = userMyWorks.child("Novels");
//        DatabaseReference userMyWorksPublic = referenceUsers.child(keyU).child("MyWorks").child("public");
//        userMyWorksPublicIllustrations= userMyWorksPublic.child("Illustration");
//        userMyWorksPublicManga= userMyWorksPublic.child("Manga");
//        userMyWorksPublicNovels = userMyWorksPublic.child("Novels");

        DatabaseReference userCollections = referenceUsers.child(keyU).child("Collections");
        userCollectionsIllustrations = userCollections.child("Illustration");
        userCollectionsManga = userCollections.child("Manga");
        userCollectionsNovels = userCollections.child("Novels");



        referenceImageUser = referenceUsers.child(keyU).child("ImageProfile");

        referenceUsers.child(keyU).child("Follows");


    }

    public static boolean[] userExists = {false,false};

    public static String getKeyU() {
        return keyU;
    }

    public static void compararUserPassword(final String password, final String userName){
        System.out.println("inici");
        referenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user!=null){
                        if(user.getUsername().equals(userName)){
                            System.out.println(user.getUsername());
                            System.out.println(userName);
                            userExists[0] = true;
                            if(user.getPassword().equals(password)){
                                keyU = user.getKey();
                                userExists[1] = true;
                            }
                        }
                    }else{

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }


    public static void subirNuevoUser(String userName, String password){
        String key = referenceUsers.push().getKey();
        keyU = key;
        referenceUsers.child(key).setValue(new User(userName, password,key, defaultImage));
    }

    public static void subirImagenPerfil(String url){
        DatabaseReference ref = referenceImageUser.child(keyU).getRef();
        ref.child("URL").setValue(url);
    }

    //region SUBIR COLLECTIONS
    public static void subirCollection(NovelClass novel){
        DatabaseReference ref = referenceUsers.child(keyU).child("collections").getRef();
        String keyI = ref.push().getKey();
        novel.setKey(keyI);
        ref.child(keyI).setValue(novel);
    }
    public static void subirCollection(MangaClass manga){
        DatabaseReference ref = referenceUsers.child(keyU).child("collections").getRef();
        String keyI = ref.push().getKey();
        manga.setKey(keyI);
        ref.child(keyI).setValue(manga);
    }
    public static void subirCollection(IllustrationClass illus){
        DatabaseReference ref = referenceUsers.child(keyU).child("collections").getRef();
        String keyI = ref.push().getKey();
        illus.setKey(keyI);
        ref.child(keyI).setValue(illus);
    }
//endregion

    //region ELIMINAR COLLECTION
    public static void eliminarCollection(IllustrationClass illus){
        DatabaseReference ref = referenceUsers.child(keyU).child("collections").getRef();
        ref.child(illus.getKey()).removeValue();
    }
    public static void eliminarCollection(MangaClass manga){
        DatabaseReference ref = referenceUsers.child(keyU).child("collections").getRef();
        ref.child(manga.getKey()).removeValue();
    }
    public static void eliminarCollection(NovelClass novel){
        DatabaseReference ref = referenceUsers.child(keyU).child("collections").getRef();
        ref.child(novel.getKey()).removeValue();
    }
//endregion

    //region SUBIR YOUR WORKS
    public static void subirMyWork(IllustrationClass illus, String myWorkPackage){
        DatabaseReference ref = referenceUsers.child(keyU).child("MyWork").child(myWorkPackage).getRef();
        String keyI = ref.push().getKey();
        illus.setKey(keyI);
        ref.child(keyI).setValue(illus);
    }
    public static void subirMyWork(MangaClass manga, String myWorkPackage){
        DatabaseReference ref = referenceUsers.child(keyU).child("MyWork").child(myWorkPackage).getRef();
        String keyI = ref.push().getKey();
        manga.setKey(keyI);
        ref.child(keyI).setValue(manga);
    }
    public static void subirMyWork(NovelClass novel, String myWorkPackage){
        DatabaseReference ref = referenceUsers.child(keyU).child("MyWork").child(myWorkPackage).getRef();
        String keyI = ref.push().getKey();
        novel.setKey(keyI);
        ref.child(keyI).setValue(novel);
    }
    //endregion

    //region ELIMINAR YOUR WORKS
    public static void eliminarMyWork(IllustrationClass illus, String myWorkPackage){
        DatabaseReference ref = referenceUsers.child(keyU).child("MyWork").child(myWorkPackage).getRef();
        ref.child(illus.getKey()).removeValue();
    }
    public static void eliminarMyWork(MangaClass manga, String myWorkPackage){
        DatabaseReference ref = referenceUsers.child(keyU).child("MyWork").child(myWorkPackage).getRef();
        ref.child(manga.getKey()).removeValue();
    }
    public static void eliminarMyWork(NovelClass novels, String myWorkPackage){
        DatabaseReference ref = referenceUsers.child(keyU).child("MyWork").child(myWorkPackage).getRef();
        ref.child(novels.getKey()).removeValue();
    }
//endregion

    public static String buscarImagenPerfil(){
        final String[] urlIU = new String[1];
        referenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    if(user.getKey().equals(keyU)){
                        urlIU[0] = user.getImatgePerfil();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return urlIU[0];
    }

    //region MANEJO_DE_IMAGEN
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void comprimirImatge(Bitmap thumb_bitmap, Context context, File url, DatabaseReference imageReference){
        byte [] thumb_byte;
        try {
            thumb_bitmap = new Compressor(context)
                    .setMaxHeight(125)
                    .setMaxWidth(125)
                    .setQuality(50)
                    .compressToBitmap(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 90,byteArrayOutputStream);
        thumb_byte = byteArrayOutputStream.toByteArray();
        pujarImatge(thumb_byte, imageReference);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pujarImatge(byte[] thumb_byte, DatabaseReference imageReference){
        final String[] nombreImagen = {""};

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        nombreImagen[0] = timestamp + ".jpg";

        final StorageReference ref = storageImageReference.child(nombreImagen[0]);
        StorageMetadata metadata = new StorageMetadata.Builder()
//                .setCustomMetadata("longitude", String.valueOf(datosRecuperados.getDouble("longitude", 0.0)))
//                .setCustomMetadata("latitude", String.valueOf(datosRecuperados.getDouble("latitude", 0.0)))
                .build();
        UploadTask uploadTask =  ref.putBytes(thumb_byte, metadata);
        Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
            if(!task.isSuccessful()){
                throw Objects.requireNonNull(task.getException());
            }
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            Uri downloadUri = task.getResult();
            imageReference.push().child("urlfoto").setValue(downloadUri.toString());
            System.out.println("Todo OK pujada feta");
            urlImage =  downloadUri.toString();
        });

    }
    //endregion


    public static FirebaseDatabase getDatabase() {
        return database;
    }


    public static DatabaseReference getReferenceIllustrationsRecommended() {
        return referenceIllustrationsRecommended;
    }

    public static DatabaseReference getReferenceIllustrationsRanking() {
        return referenceIllustrationsRanking;
    }

    public static DatabaseReference getReferenceIllustrationsPopularLives() {
        return referenceIllustrationsPopularLives;
    }

    public static DatabaseReference getReferenceMangaRecommended() {
        return referenceMangaRecommended;
    }

    public static DatabaseReference getReferenceMangaRanking() {
        return referenceMangaRanking;
    }

    public static DatabaseReference getReferenceMangaPixivVision() {
        return referenceMangaPixivVision;
    }

    public static DatabaseReference getReferenceNovelsRecommended() {
        return referenceNovelsRecommended;
    }

    public static DatabaseReference getReferenceNovelsRanking() {
        return referenceNovelsRanking;
    }

    public static String getUrlImage() {
        return urlImage;
    }

    public static DatabaseReference getReferenceUsers() {
        return referenceUsers;
    }

    public static DatabaseReference getReferenceImage() {
        return referenceImage;
    }

    public static DatabaseReference getReferenceImageUser() {
        return referenceImageUser;
    }

    public static String getDefaultImage() {
        return defaultImage;
    }

    public static StorageReference getStorageImageReference() {
        return storageImageReference;
    }

    public static DatabaseReference getUserMyWorksIllustrations() {
        return userMyWorksIllustrations;
    }

    public static DatabaseReference getUserMyWorksManga() {
        return userMyWorksManga;
    }

    public static DatabaseReference getUserMyWorksNovels() {
        return userMyWorksNovels;
    }

    public static DatabaseReference getUserMyWorksPublicIllustrations() {
        return userMyWorksPublicIllustrations;
    }

    public static DatabaseReference getUserMyWorksPublicManga() {
        return userMyWorksPublicManga;
    }

    public static DatabaseReference getUserMyWorksPublicNovels() {
        return userMyWorksPublicNovels;
    }

    public static DatabaseReference getUserCollectionsIllustrations() {
        return userCollectionsIllustrations;
    }

    public static DatabaseReference getUserCollectionsManga() {
        return userCollectionsManga;
    }

    public static DatabaseReference getUserCollectionsNovels() {
        return userCollectionsNovels;
    }
}


