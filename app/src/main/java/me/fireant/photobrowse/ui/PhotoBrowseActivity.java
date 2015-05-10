package me.fireant.photobrowse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.fireant.photobrowse.R;
import me.fireant.photobrowse.adapter.PhotoAdapter;
import me.fireant.photobrowse.utils.TypefaceUtils;
import me.fireant.photobrowse.widget.HackyViewPager;


public class PhotoBrowseActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_IMAGES = "bundle_key_images";
    private static final String BUNDLE_KEY_INDEX = "bundle_key_index";

    @InjectView(R.id.viewpager)
    HackyViewPager mViewpager;
    @InjectView(R.id.tv_icon)
    TextView mTvIcon;
    @InjectView(R.id.tv_photo_index)
    TextView mTvPhotoIndex;

    private PhotoAdapter adapter;

    private String[] imageUrls;

    private int index = 0;

    /***
     * 显示图片浏览器界面
     * @param context context
     * @param images 图片url数组
     * @param index 初始显示的下标
     */
    public static void showPhotoBrowse(Context context, String[] images, int index) {
        Intent intent = new Intent();
        intent.setClass(context, PhotoBrowseActivity.class);
        intent.putExtra(BUNDLE_KEY_INDEX, index);
        intent.putExtra(BUNDLE_KEY_IMAGES, images);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_browse);
        ButterKnife.inject(this);

        adapter = new PhotoAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);

        final Intent intent = getIntent();
        if (intent != null) {
            index = intent.getIntExtra(BUNDLE_KEY_INDEX, 1);
            imageUrls = intent.getStringArrayExtra(BUNDLE_KEY_IMAGES);
        } else {
            imageUrls = new String[]{
                    "https://is3-ssl.mzstatic.com/image/thumb/Purple7/v4/8a/46/48/8a4648e7-9a7f-1f84-d80a-47349391c206/pr_source.jpg/500x500bb-80.png",
                    "http://s11.sinaimg.cn/bmiddle/4f0516b944cdb850877ca",
                    "http://git.oschina.net/uploads/68/6168_fireant.jpeg?1430822294"
            };
        }

        if (imageUrls != null && imageUrls.length > 0) {
            adapter.add(imageUrls);
            setIndex();
            mViewpager.setCurrentItem(index);
            mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    index = position;
                    setIndex();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            TypefaceUtils.setSemTextIcon(mTvIcon);
        } else {
            Toast.makeText(this, "哥们，给我一个图片url呗", Toast.LENGTH_LONG).show();
        }
    }

    private void setIndex() {
        mTvPhotoIndex.setText((index + 1) + "/" + imageUrls.length);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
