package com.example.edogawa.filmku.data.offline;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Edogawa on 12/3/2017.
 */

public class MovieContract {
    public static final String AUTHORITY = "com.example.edogawa.filmku";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "tb_movies";

        public static final String COLUMN_FAVORITE_IDS = "ids";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_VOTE_AVG = "vote_average";
        public static final String COLUMN_ORI_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANG = "original_languange";
        public static final String COLUMN_GENRE = "genre";
    }
}
