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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

import static ua.com.adr.android.englishforkids.R.id.tvPage_rep;
import static ua.com.adr.android.englishforkids.R.id.tvPage_rep_alt;
import static ua.com.adr.android.englishforkids.R.id.tv_translate_rep;

public class RepeatActivity extends FragmentActivity  {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 1000;
    static int counterPages;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    StudyActivity.PageFragment mPageFragment;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    TextView tvPageNumber;

    int[] soundsArray = new int[15];
    static int[] numberArray = new int[15];
    String[] toysSounds = {"ball.ogg", "doll.ogg", "train.ogg", "car.ogg", "plane.ogg", "bike.ogg", "teddy_bear.ogg", "drum.ogg", "bell.ogg", "balloon.ogg",
            "kite.ogg", "ship.ogg", "dolls_house.ogg", "scooter.ogg", "computer.ogg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

        pager = (ViewPager) findViewById(R.id.pager_repeat);
        pagerAdapter = new RepeatActivity.MyFragmentPagerAdapter(getSupportFragmentManager());
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
        Button next = (Button) findViewById(R.id.buttonNext);
        next.bringToFront();
        next.setOnClickListener(onClickListener);



        for (int i = 0; i < 15; i++)
            soundsArray[i] = loadSound(toysSounds[i]);

        for (int i = 0; i < 15; i++) {
            numberArray[i] = i;
        }
        shuffleArray(numberArray);



        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
              //  Log.d(TAG, "onPageSelected, position = " + position);
                if (counterPages%2!=0) {
                    int index2 = ((counterPages + 1) / 2) - 1;
                    int soundnumber = index2 - 1;
                    if (soundnumber == -1) {
                        soundnumber = 14;
                    }
                    playSound(soundsArray[numberArray[soundnumber]]);
                    Log.d(TAG, "SoundPlay, pageNumber = " + index2);
                }
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



    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RepeatActivity.PageFragment.newInstance(position);
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

                case R.id.buttonNext:
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
        static int pageNumber;
        String[] words = {"ball", "doll", "train", "car", "plane", "bike", "teddy bear", "drum", "bell", "balloon", "kite", "ship", "doll's house", "scooter", "computer" };
        int[] translate = {R.string.ball, R.string.doll, R.string.train, R.string.car, R.string.plane, R.string.bike, R.string.teddy_bear, R.string.drum, R.string.bell,
                R.string.balloon, R.string.kite, R.string.ship, R.string.dools_house, R.string.scooter, R.string.computer};
        int[] images = {R.drawable.ball, R.drawable.doll, R.drawable.train, R.drawable.car, R.drawable.plane, R.drawable.bike, R.drawable.teddybear, R.drawable.drum, R.drawable.bell,
                R.drawable.balloon, R.drawable.kite, R.drawable.ship, R.drawable.dollshouse, R.drawable.scooter, R.drawable.computer};

        String[] toysSounds = {"ball.ogg", "doll.ogg", "train.ogg", "car.ogg", "plane.ogg", "bike.ogg", "teddy_bear.ogg", "drum.ogg", "bell.ogg", "balloon.ogg",
                "kite.ogg", "ship.ogg", "dolls_house.ogg", "scooter.ogg", "computer.ogg"};

        static RepeatActivity.PageFragment newInstance(int page) {
            RepeatActivity.PageFragment pageFragment = new RepeatActivity.PageFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
            pageFragment.setArguments(arguments);
            return pageFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER)%15;
          //  pageNumber = randomPage();
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
            View view;
            if (counterPages%2!=0) {
                int index2 = ((counterPages + 1)/ 2) - 1;
                view = inflater.inflate(R.layout.fragment_repeat, null);

                TextView tvWord = (TextView) view.findViewById(tvPage_rep);
                tvWord.setText(words[numberArray[index2]]);

                Log.d(TAG, "PictirePlay, index2 = " + index2);

                TextView tvTranslate = (TextView) view.findViewById(tv_translate_rep);
                tvTranslate.setText(translate[numberArray[index2]]);


                ImageView picture = (ImageView) view.findViewById(R.id.iv_new_word_rep);
                picture.setImageResource(images[numberArray[index2]]);
            }
            else {
                int index1 = ((counterPages + 2)/ 2 ) - 1;
                view = inflater.inflate(R.layout.fragment_repeat_space, null);
                TextView tvWord = (TextView) view.findViewById(tvPage_rep_alt);
                tvWord.setText(words[numberArray[index1]]);

                Log.d(TAG, "PustoPlay, index1 = " + index1);



                ImageView picture = (ImageView) view.findViewById(R.id.iv_new_word_rep_alt);
                picture.setImageResource(R.drawable.img_placeholder);

                //pageNumber = pageNumber/2;
            }
            counterPages++;
            if (counterPages == 30) counterPages = 0;


            // tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);
            // tvPageNumber.setText(pageNumber + "/15");

            // Log.d(TAG, "onPageSelected, pageNumber = " + pageNumber + " " + soundsArray[pageNumber]);
           // playSound(soundsArray[numberArray[pageNumber]]);



            return view;
        }

//        public ViewPager.OnPageChangeListener player = new ViewPager.OnPageChangeListener()
//        {
//
//            @Override
//            public void onPageSelected(int arg1)
//            {
//                playSound(soundsArray[pageNumber]);
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2)
//            {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0)
//            {
//            }
//        };

//        View.OnClickListener onClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.buttonNext:
//                        //        playSound(loadSound(toysSounds [pageNumber]));
//                        pager.setCurrentItem(pager.getCurrentItem()+1);
//                        break;
//                }
//            }
//        };

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
