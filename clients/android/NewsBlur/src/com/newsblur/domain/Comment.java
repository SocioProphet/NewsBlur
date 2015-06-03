package com.newsblur.domain;

import java.io.Serializable;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.newsblur.database.DatabaseConstants;

public class Comment implements Serializable {
	private static final long serialVersionUID = -2018705258520565390L;

    // we almost always override API version with sensible PK constructed by concating story, feed, and user IDs
	public String id;

	@SerializedName("comments")
	public String commentText;

	@SerializedName("user_id")
	public String userId;

	@SerializedName("shared_date")
	public String sharedDate;
	
	@SerializedName("source_user_id")
	public String sourceUserId;

	@SerializedName("date")
	public String date;

	@SerializedName("liking_users")
	public String[] likingUsers = new String[]{};
	
	public Reply[] replies;
	
	public String storyId;
	
    // not vended by API, but we set it depending on which comment block of the response in which it appeared
	public boolean byFriend = false;

    // means this "comment" is actually a text-less share, which looks a little bit different
    public boolean isPseudo = false;

	public ContentValues getValues() {
		ContentValues values = new ContentValues();
		values.put(DatabaseConstants.COMMENT_DATE, date);
		values.put(DatabaseConstants.COMMENT_STORYID, storyId);
		values.put(DatabaseConstants.COMMENT_LIKING_USERS, TextUtils.join(",", likingUsers));
		values.put(DatabaseConstants.COMMENT_TEXT, commentText);
		values.put(DatabaseConstants.COMMENT_SHAREDDATE, sharedDate);
		values.put(DatabaseConstants.COMMENT_BYFRIEND, byFriend ? "true" : "false");
		values.put(DatabaseConstants.COMMENT_SOURCE_USERID, sourceUserId);
		values.put(DatabaseConstants.COMMENT_USERID, userId);
		values.put(DatabaseConstants.COMMENT_ID, id);
		values.put(DatabaseConstants.COMMENT_ISPSEUDO, isPseudo ? "true" : "false");
		return values;
	}

	public static Comment fromCursor(final Cursor cursor) {
		
		Comment comment = new Comment();
		comment.date = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_DATE));
		comment.sharedDate = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_SHAREDDATE));
		comment.commentText = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_TEXT));
		comment.storyId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_STORYID));
		comment.userId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_USERID));
		comment.byFriend = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_BYFRIEND)));
		String likingUsers = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_LIKING_USERS));
		comment.likingUsers = TextUtils.split(likingUsers, ",");
		comment.sourceUserId = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_SOURCE_USERID));
		comment.id = cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_ID));
		comment.isPseudo = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DatabaseConstants.COMMENT_ISPSEUDO)));
		return comment;
	}

}
