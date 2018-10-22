package com.wendy.instagramviewer.api.StaticTags;

public class API_Tags
{
    // user table tags
    public static final String USR_NAME = "user_name=";
    public static final String USR_PWD = "user_password=";
    public static final String USR_EMAIL = "user_email=";
    public static final String REG_TIME = "register_time=";
    public static final String ICON_NAME = "icon_name=";
    public static final String FOLLOWERS = "followers=";
    public static final String FOLLOWEES = "followees=";
    // media table tags
    public static final String MED_NAME = "media_name=";
    public static final String MED_TIME = "media_time=";
    public static final String MED_LOC = "media_location=";
    public static final String MED_DESP = "media_description=";
    // comment table tags
    public static final String CMTOR_NAME = "commentor_name=";
    public static final String CMT_TEXT = "comment_text=";
    public static final String AT_NAMES = "at_names=";
    public static final String CMT_TIME = "comment_time=";
    public static final String CMT_LOC = "comment_location=";
    // event table tags
    public static final String EVT_ID = "event_identifier";
    public static final String EVT_LIKE = "%LIKED%";
    public static final String EVT_CMT = "%CMT%";
    public static final String EVT_FLW = "%FLW%";
    public static final String EVT_TIME = "event_time";
    // database default values
    public static final String LIKED_VAL = "%LIKED%";
    public static final String NONE_VAL = "%NONE%";
    // server returned values
    public static final String RTN_SUCC = "%SUCCESS%";
    public static final String RTN_FAIL = "%FAIL%";
    // other tags
    public static final String TGT_USR = "target_user=";
    public static final String KEY_WORD = "key_word=";
    public static final String HTTP = "http://";
    // API names
    public static final String USER_REG = "/UserReg?";
    public static final String SEARCH_USER = "/SearchUsers?";
    public static final String GET_USER_MEDIA = "/GetUserMedia?";
    public static final String GET_USER_MEDIA_COMMENTS = "/GetUserMediaComments?";
    public static final String UPLOAD_USER_MEDIA = "/UploadUserMedia?";
    public static final String COMMENT_USER_MEDIA = "/CommentOnMedia?";
    public static final String AUTH_USER = "/AuthenticateUser?";
    public static final String RCMD_IMAGE_USER = "/RecommendImageUser?";
    public static final String RCMD_CMT_USER = "/RecommendCommentUser?";
    public static final String UPLOAD_USER_ICON = "/UploadUserIcon?";
    public static final String GET_USER_ICON = "/GetUserIcon?";
    public static final String GET_MEDIA_LIKERS = "/GetUserMediaLikers?";
    public static final String GET_MEDIA_DATE_SORT = "/GetUserMediaDateSort?";
    public static final String GET_MEDIA_LOC_SORT = "/GetUserMediaLocationSort?";
    public static final String USER_DEL = "/UserDel?";
    public static final String DEL_MEDIA = "/RemoveUserMedia?";
    public static final String FOLLOW_USER = "/FollowUsers?";
    public static final String UNFOLLOW_USER = "/UnfollowUsers?";
    public static final String UNCOMMENT_USER_MEDIA = "/UncommentOnMedia?";
    public static final String LIKE_USER_MEDIA = "/LikeUserMedia?";
    public static final String UNLIKE_USER_MEDIA = "/UnlikeUserMedia?";
    public static final String GET_ALL_USER_MEDIA_NAMES = "/GetAllUserMediaNames?";
    public static final String GET_ALL_USER_MEDIA_INFO = "/GetAllUserMediaInfo?";
    public static final String GET_TGT_USER_ICON = "/GetTargetUserIcon?";
    public static final String GET_TGT_USER_MEDIA = "/GetTargetUserMedia?";
    public static final String GET_ALL_USER_NAMES = "/GetAllUserNames?";
    public static final String GET_USER_EVENTS = "/GetUserEvent?";
    public static final String GET_MED_DESC = "/GetMediaDescription?";
    public static final String GET_USER_FOLLOWERS = "/GetUserFollowers?";
    public static final String GET_USER_FOLLOWINGS = "/GetUserFollowings?";
    public static final String GET_TARGET_USER_MEDIA_INFO = "/GetTargetUserMediaInfo?";

}
