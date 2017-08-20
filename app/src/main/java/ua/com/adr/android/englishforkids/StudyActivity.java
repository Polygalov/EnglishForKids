package ua.com.adr.android.englishforkids;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static ua.com.adr.android.englishforkids.R.id.tvPage;
import static ua.com.adr.android.englishforkids.R.id.tv_transcript;
import static ua.com.adr.android.englishforkids.R.id.tv_translate;

public class StudyActivity extends FragmentActivity {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 15;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    PageFragment mPageFragment;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    TextView tvPageNumber;
    ImageButton leftArrow, rightArrow;
    int[] soundsArray = new int[15];
    String[] toysSounds = {"ball.ogg", "doll.ogg", "train.ogg", "car.ogg", "plane.ogg", "bike.ogg", "teddy_bear.ogg", "drum.ogg", "bell.ogg", "balloon.ogg",
            "kite.ogg", "ship.ogg", "dolls_house.ogg", "scooter.ogg", "computer.ogg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();
        tvPageNumber = (TextView) findViewById(R.id.tvPageNumber);
        leftArrow = (ImageButton) findViewById(R.id.left_nav);
        rightArrow = (ImageButton) findViewById(R.id.right_nav);
        leftArrow.setOnClickListener(onClickListener);
        rightArrow.setOnClickListener(onClickListener);

        // получим идентификаторы

        for (int i = 0; i < 15; i++)
            soundsArray[i] = loadSound(toysSounds[i]);

        final Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playSound(soundsArray[0]);
            }
        });

        myThread.start(); // запускаем

        // playSound(soundsArray[0]);

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
                tvPageNumber.setText(position+1 + "/15");
                playSound(soundsArray[position]);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();

            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_nav:
                    if(pager.getCurrentItem()>0) pager.setCurrentItem(pager.getCurrentItem()-1);
                    break;
                case R.id.right_nav:
                    //  if(pager.getCurrentItem()<(pager.getChildCount()-1))
                    pager.setCurrentItem(pager.getCurrentItem()+1);
                    break;
            }
        }
    };

    public static class PageFragment extends Fragment {

        static final String TAG = "myLogs";
        static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
        private SoundPool mSoundPool;
        private AssetManager mAssetManager;
        private TextView tvPageNumber;
        int[] soundsArray = new int[15];
        private int mStreamID;
        int pageNumber;
        String[] words = {"ball", "doll", "train", "car", "plane", "bike", "teddy bear", "drum", "bell", "balloon", "kite", "ship", "doll's house", "scooter", "computer" };
        int[] translate = {R.string.ball, R.string.doll, R.string.train, R.string.car, R.string.plane, R.string.bike, R.string.teddy_bear, R.string.drum, R.string.bell,
                R.string.balloon, R.string.kite, R.string.ship, R.string.dools_house, R.string.scooter, R.string.computer};
        int[] images = {R.drawable.ball, R.drawable.doll, R.drawable.train, R.drawable.car, R.drawable.plane, R.drawable.bike, R.drawable.teddybear, R.drawable.drum, R.drawable.bell,
                R.drawable.balloon, R.drawable.kite, R.drawable.ship, R.drawable.dollshouse, R.drawable.scooter, R.drawable.computer};
        String[] transcriptions = {"[ bɔːl ]", "[ dɑːl ]", "[ treɪn ]", "[ kɑːr ]", "[ pleɪn ]", "[ baɪk ]", "[ ˈted.i ber ]", "[ drʌm ]", "[ bel ]",
                "[ bəˈluːn ]", "[ kaɪt ]", "[ ʃɪp ]", "[ dɑːls haʊs]", "[ ˈskuː.tər ]", "[ kəmˈpjuːtər ]"};
        String[] toysSounds = {"ball.ogg", "doll.ogg", "train.ogg", "car.ogg", "plane.ogg", "bike.ogg", "teddy_bear.ogg", "drum.ogg", "bell.ogg", "balloon.ogg",
                "kite.ogg", "ship.ogg", "dolls_house.ogg", "scooter.ogg", "computer.ogg"};

        static PageFragment newInstance(int page) {
            PageFragment pageFragment = new PageFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
            pageFragment.setArguments(arguments);
            return pageFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // Для устройств до Android 5
                createOldSoundPool();
            } else {
                // Для новых устройств
                createNewSoundPool();
            }

            mAssetManager = getActivity().getAssets();

            // получим идентификаторы

            for (int i = 0; i < 15; i++)
                soundsArray[i] = loadSound(toysSounds[i]);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment, null);

            TextView tvWord = (TextView) view.findViewById(tvPage);
            tvWord.setText(words [pageNumber]);

            TextView tvTranscript = (TextView) view.findViewById(tv_transcript);
            tvTranscript.setText(transcriptions [pageNumber]);

            TextView tvTranslate = (TextView) view.findViewById(tv_translate);
            tvTranslate.setText(translate [pageNumber]);

            ImageButton sound = (ImageButton) view.findViewById(R.id.ib_sound);
            sound.setImageResource(R.drawable.ic_lock_silent_mode_off);
            sound.setOnClickListener(onClickListener);

            ImageView picture = (ImageView) view.findViewById(R.id.iv_new_word);
            picture.setImageResource(images [pageNumber]);

            // tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);
            // tvPageNumber.setText(pageNumber + "/15");

            // Log.d(TAG, "onPageSelected, pageNumber = " + pageNumber + " " + soundsArray[pageNumber]);
            //playSound(soundsArray[pageNumber]);



            return view;
        }

        public ViewPager.OnPageChangeListener player = new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg1)
            {
                playSound(soundsArray[pageNumber]);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        };

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ib_sound:
                        //        playSound(loadSound(toysSounds [pageNumber]));
                        Log.d(TAG, "pushButton, pageNumber = " + pageNumber + " " + soundsArray[pageNumber]);
                        playSound(soundsArray[pageNumber]);
                        break;
                }
            }
        };

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void createNewSoundPool() {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }

        @SuppressWarnings("deprecation")
        private void createOldSoundPool() {
            mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

        private int playSound(int sound) {
            if (sound > 0) {
                mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
            }
            return mStreamID;
        }

        private int loadSound(String fileName) {
            AssetFileDescriptor afd;
            try {
                afd = mAssetManager.openFd(fileName);
            } catch (IOException e) {
                e.printStackTrace();

                return -1;
            }
            return mSoundPool.load(afd, 1);
        }
    }


}
