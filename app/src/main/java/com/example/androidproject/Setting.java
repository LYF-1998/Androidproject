package com.example.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {
    private static final int PICK_PHOTO = 0x00;
    private final int CAMERA_REQUEST = 0x01;
    Button button1,button2,button3,button4;
    TextView username,number;
    ImageView setImage,headImage;
    UserInfo instance = UserInfo.getInstance();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        new Thread(future).start();

        button1 = getActivity().findViewById(R.id.orderbutton);
        button2 = getActivity().findViewById(R.id.addbutton);
        button3 = getActivity().findViewById(R.id.collectionbutton);
        button4 = getActivity().findViewById(R.id.serverbutton);
        username = getActivity().findViewById(R.id.username);
        number = getActivity().findViewById(R.id.number);
        setImage = getActivity().findViewById(R.id.setting_set);
        headImage = getActivity().findViewById(R.id.setting_headImage);
        username.setText(instance.username);
        number.setText(instance.phone);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(userInformationInterface.this,"button1",Toast.LENGTH_SHORT).show();
                //订单页面
                Intent intent = new Intent(getActivity(), K_BusinessOrder.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(userInformationInterface.this,"button2",Toast.LENGTH_SHORT).show();
                //地址页面
                Intent intent = new Intent(getActivity(), K_Address.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInformationInterface.this,"button3",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), K_AboutUs.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInformationInterface.this,"button4",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), K_Server.class);
                startActivity(intent);
            }
        });
        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Set.class);
                startActivity(intent);
            }
        });
        headImage.setOnClickListener((v)->{
            String[] items = {"从图库中选择","拍照"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("修改头像");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0){
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.setType("image/*");
                        startActivityForResult(intent,PICK_PHOTO);

                    }else if (which == 1){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//实例化Intent对象,使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
                        startActivityForResult(intent,CAMERA_REQUEST);//开启相机，传入上面的Intent对象
                    }
                }
            });
            builder.show();
        });
    }

    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//    }
    //跳转至相册界面
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, PICK_PHOTO);
    }

    //返回接收图片，其中getBitmapFromUri()为根据Uri获取（ 优化后的）图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            Uri uri = data.getData();
            Bitmap bitmap = getBitmapFromUri(getActivity(), uri);
            ImageView iv = getActivity().findViewById(R.id.setting_headImage);
            iv.setImageBitmap(bitmap);
        }else if (requestCode == CAMERA_REQUEST) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");//这里已经获取了Bitmap格式的图片对象
            ImageView iv = getActivity().findViewById(R.id.setting_headImage);//获取ImageView来显示
            iv.setImageBitmap(bitmap);
        }
    }

    //根据Uri获取图片，这个代码片格式
    public static Bitmap getBitmapFromUri(Activity activity, Uri uri) {
        Bitmap bm = null;
        InputStream is = null;
        try {
            is = activity.getContentResolver().openInputStream(uri);
            bm = BitmapFactory.decodeStream(is);
            //bm = getBitmapFormUri(activity, uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

//    //按屏幕压缩图片
//    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
//        InputStream input = ac.getContentResolver().openInputStream(uri);
//        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
//        onlyBoundsOptions.inJustDecodeBounds = true;
//        onlyBoundsOptions.inDither = true;//optional
//        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
//        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
//        input.close();
//        //获取图片宽高
//        int imageWidth = onlyBoundsOptions.outWidth;
//        int imageHeight = onlyBoundsOptions.outHeight;
//        //获取屏幕宽高
//        Display dp = ac.getWindowManager().getDefaultDisplay();
//        int screenWidth = dp.getWidth();
//        int screenHeight = dp.getHeight();
//        int scale = 1;
//        //设置缩放比例，哪个比例大，用哪个
//        int scaleWidth = imageWidth / screenWidth;
//        int scaleHeight = imageHeight / screenHeight;
//        if (scaleWidth >= scaleHeight && scaleWidth > 0) {
//            scale = scaleWidth;
//        } else if (scaleWidth < scaleHeight) {
//            scale = scaleHeight;
//        }
//        if (scale <= 0)
//            scale = 1;
//        //比例压缩
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inSampleSize = scale;//设置缩放比例
//        bitmapOptions.inDither = true;//optional
//        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
//        //前一个输入流被读完了，重新建立输入流，否则读不出图片，这是个坑!
//        input = ac.getContentResolver().openInputStream(uri);
//        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
//        input.close();
//        return compressImage(bitmap);//再进行质量压缩
//    }
//
//    //质量压缩方法
//    public static Bitmap compressImage(Bitmap image) {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }
}