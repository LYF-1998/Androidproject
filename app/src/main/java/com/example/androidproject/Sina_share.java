package com.example.androidproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.androidproject.Tencent_share.AppConstants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Sina_share extends Activity implements OnClickListener, IWeiboHandler.Response {
    @SuppressWarnings("unused")
    private static final String TAG = "WBShareActivity";

    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;
    public static final int SHARE_ALL_IN_ONE = 2;

    private TextView        mTitleView;
    private ImageView       mImageView;
    private CheckBox        mTextCheckbox;
    private CheckBox        mImageCheckbox;
    private WBShareItemView mShareWebPageView;
    private WBShareItemView mShareMusicView;
    private WBShareItemView mShareVideoView;
    private WBShareItemView mShareVoiceView;
    private Button          mSharedBtn;
    private IWeiboShareAPI  mWeiboShareAPI = null;
    private int mShareType = SHARE_CLIENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sina_share);
        initViews();

        mShareType = getIntent().getIntExtra(KEY_SHARE_TYPE, SHARE_CLIENT);

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, AppConstants.APP_KEY);

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }


    @Override
    public void onResponse(BaseResponse baseResp) {
        if(baseResp!= null){
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this,
                            getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (R.id.share_to_btn == v.getId()) {
            sendMessage(mTextCheckbox.isChecked(),
                    mImageCheckbox.isChecked(),
                    mShareWebPageView.isChecked(),
                    mShareMusicView.isChecked(),
                    mShareVideoView.isChecked(),
                    mShareVoiceView.isChecked());
        }
    }


    private void initViews() {
        mTitleView = (TextView) findViewById(R.id.share_title);
        mTitleView.setText(R.string.weibosdk_demo_share_to_weibo_title);
        mImageView = (ImageView) findViewById(R.id.share_imageview);
        mTextCheckbox = (CheckBox) findViewById(R.id.share_text_checkbox);
        mImageCheckbox = (CheckBox) findViewById(R.id.shared_image_checkbox);

        mSharedBtn = (Button) findViewById(R.id.share_to_btn);
        mSharedBtn.setOnClickListener(this);

        mShareWebPageView = (WBShareItemView)findViewById(R.id.share_webpage_view);
        mShareMusicView = (WBShareItemView)findViewById(R.id.share_music_view);
        mShareVideoView = (WBShareItemView)findViewById(R.id.share_video_view);
        mShareVoiceView = (WBShareItemView)findViewById(R.id.share_voice_view);
        mShareWebPageView.setOnCheckedChangeListener(mCheckedChangeListener);
        mShareMusicView.setOnCheckedChangeListener(mCheckedChangeListener);
        mShareVideoView.setOnCheckedChangeListener(mCheckedChangeListener);
        mShareVoiceView.setOnCheckedChangeListener(mCheckedChangeListener);

        mShareWebPageView.initWithRes(
                R.string.weibosdk_demo_share_webpage_title,
                R.mipmap.atb,
                R.string.weibosdk_demo_share_webpage_title,
                R.string.weibosdk_demo_share_webpage_desc,
                R.string.weibosdk_demo_test_webpage_url);

        mShareMusicView.initWithRes(
                R.string.weibosdk_demo_share_music_title,
                R.drawable.ic_share_music_thumb,
                R.string.weibosdk_demo_share_music_title,
                R.string.weibosdk_demo_share_music_desc,
                R.string.weibosdk_demo_test_music_url);

        mShareVideoView.initWithRes(
                R.string.weibosdk_demo_share_video_title,
                R.drawable.ic_share_video_thumb,
                R.string.weibosdk_demo_share_video_title,
                R.string.weibosdk_demo_share_video_desc,
                R.string.weibosdk_demo_test_video_url);

        mShareVoiceView.initWithRes(
                R.string.weibosdk_demo_share_voice_title,
                R.drawable.ic_share_voice_thumb,
                R.string.weibosdk_demo_share_voice_title,
                R.string.weibosdk_demo_share_voice_desc,
                R.string.weibosdk_demo_test_voice_url);
    }


    private WBShareItemView.OnCheckedChangeListener mCheckedChangeListener = new WBShareItemView.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(WBShareItemView view, boolean isChecked) {
            mShareWebPageView.setIsChecked(false);
            mShareMusicView.setIsChecked(false);
            mShareVideoView.setIsChecked(false);
            mShareVoiceView.setIsChecked(false);

            view.setIsChecked(isChecked);
        }
    };


    private void sendMessage(boolean hasText, boolean hasImage,
                             boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {

        if (mShareType == SHARE_CLIENT) {
            if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
                if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                    sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
                } else {
                    sendSingleMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo/*, hasVoice*/);
                }
            } else {
                Toast.makeText(this, R.string.weibosdk_demo_not_support_api_hint, Toast.LENGTH_SHORT).show();
            }
        }
        else if (mShareType == SHARE_ALL_IN_ONE) {
            sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
        }
    }

    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
                                  boolean hasMusic, boolean hasVideo, boolean hasVoice) {

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }

        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }

        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (hasWebpage) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (hasMusic) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (hasVideo) {
            weiboMessage.mediaObject = getVideoObj();
        }
        if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        if (mShareType == SHARE_CLIENT) {
            mWeiboShareAPI.sendRequest(Sina_share.this, request);
        }
        else if (mShareType == SHARE_ALL_IN_ONE) {
            AuthInfo authInfo = new AuthInfo(this, AppConstants.APP_KEY, AppConstants.REDIRECT_URL, AppConstants.SCOPE);
            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
            String token = "";
            if (accessToken != null) {
                token = accessToken.getToken();
            }
            mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException( WeiboException arg0 ) {
                }

                @Override
                public void onComplete( Bundle bundle ) {
                    // TODO Auto-generated method stub
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
                    Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
                                   boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {

        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        if (hasText) {
            weiboMessage.mediaObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.mediaObject = getImageObj();
        }
        if (hasWebpage) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (hasMusic) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (hasVideo) {
            weiboMessage.mediaObject = getVideoObj();
        }
        /*if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }*/

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(Sina_share.this, request);
    }

    /**
     * 获取分享的文本模板。
     *
     * @return 分享的文本模板
     */
    private String getSharedText() {
        int formatId = R.string.weibosdk_demo_share_text_template;
        String format = getString(formatId);
        String text = format;
        String demoUrl = getString(R.string.weibosdk_demo_app_url);
        if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_text_template);
        }
        if (mShareWebPageView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_webpage_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_webpage_demo), demoUrl);
        }
        if (mShareMusicView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_music_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_music_demo), demoUrl);
        }
        if (mShareVideoView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_video_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_video_demo), demoUrl);
        }
        if (mShareVoiceView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_voice_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_voice_demo), demoUrl);
        }

        return text;
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.atb);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = mShareWebPageView.getTitle();
        mediaObject.description = mShareWebPageView.getShareDesc();

        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.atb);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = mShareWebPageView.getShareUrl();
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj() {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = mShareMusicView.getTitle();
        musicObject.description = mShareMusicView.getShareDesc();

        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.atb);



        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(bitmap);
        musicObject.actionUrl = mShareMusicView.getShareUrl();
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = "Music 默认文案";
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj() {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = mShareVideoView.getTitle();
        videoObject.description = mShareVideoView.getShareDesc();
        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share_video_thumb);
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。


        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
            System.out.println("kkkkkkk    size  "+ os.toByteArray().length );
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("Weibo.BaseMediaObject", "put thumb failed");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = mShareVideoView.getShareUrl();
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 默认文案";
        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj() {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = mShareVoiceView.getTitle();
        voiceObject.description = mShareVoiceView.getShareDesc();
        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.atb);
        // 设置 Bitmap 类型的图片到视频对象里      设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        voiceObject.setThumbImage(bitmap);
        voiceObject.actionUrl = mShareVoiceView.getShareUrl();
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = "Voice 默认文案";
        return voiceObject;
    }
}
