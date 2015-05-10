package me.fireant.photobrowse.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.fireant.photobrowse.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 火蚁 on 15/5/10.
 */
public class PhotoFragment extends Fragment {

    @InjectView(R.id.image)
    PhotoView image;
    @InjectView(R.id.loading)
    ProgressBar loading;

    private String imageUrl;

    private PhotoViewAttacher attacher;

    public static PhotoFragment newInstance(String imageUrl) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("imageUrl", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.photo_item, container, false);
        ButterKnife.inject(this, root);
        loadImage();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            imageUrl = args.getString("imageUrl");
        }
    }

    private void loadImage() {
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {
            attacher = new PhotoViewAttacher(image);
            attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    getActivity().finish();
                }
            });
            loading.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(imageUrl)
                    .error(R.drawable.ic_picture_loadfailed)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .crossFade()
                    .fitCenter()
                    .into(image);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
