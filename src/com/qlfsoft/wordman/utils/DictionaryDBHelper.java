package com.qlfsoft.wordman.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.model.BookBook;
import com.qlfsoft.wordman.model.BookCategory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DictionaryDBHelper {

	private Context mContext;
	private DictionaryDBHelper()
	{
		this.mContext = BaseApplication.getContext();
	}
	
	private static DictionaryDBHelper instance;
	
	public synchronized static DictionaryDBHelper getInstance()
	{
		if(null == instance)
			instance = new DictionaryDBHelper();
		return instance;
	}
	
	/**
	 * ��assert�ļ����и������ݿ⵽�����DataĿ¼��
	 * @return
	 */
	public boolean CopyDataBase()
	{	
		String basePath = "";
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable())
		{
			basePath = mContext.getExternalFilesDir(null).getAbsolutePath();
		}else
		{
			basePath = mContext.getFilesDir().getAbsolutePath();
		}
		if("".equals(basePath))
		{
			return false;
		}
		try{
			BaseApplication.book_db_Path = basePath + "/book.db";
			BaseApplication.dic_db_Path = basePath + "/dic.db";
			BaseApplication.dictionary_db_Path = basePath + "/dictionary.db";
			File file1 = new File(BaseApplication.book_db_Path);
			LogUtils.Logv("��ʼ��������");
			if(!file1.exists())
			{
				CopyFile("book.db",BaseApplication.book_db_Path);
				LogUtils.Logv("book.db�������");
			}
			File file2 = new File(BaseApplication.dic_db_Path);
			if(!file2.exists())
			{
				CopyFile("dic.db",BaseApplication.dic_db_Path);
				LogUtils.Logv("dic.db�������");
			}
			File file3 = new File(BaseApplication.dictionary_db_Path);
			if(!file3.exists())
			{
				CopyFile("dictionary.db",BaseApplication.dictionary_db_Path);
			}
			LogUtils.Logv("dictionary.db�������");
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * �����ļ�
	 * @param srcFile assert��Դ�ļ�·��
	 * @param destFile Ŀ���ļ�·��
	 * @throws IOException
	 */
	private void CopyFile(String srcFile,String destFile) throws IOException
	{
		LogUtils.Logv(srcFile);
		InputStream is = mContext.getResources().getAssets().open(srcFile);
		
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(destFile);
		int length;
		while((length = is.read(buffer)) !=-1 )
		{
			fos.write(buffer,0,length);
		}
		is.close();
		fos.close();
	}
	
	private SQLiteDatabase getbookDB()
	{	
		SQLiteDatabase db = mContext.openOrCreateDatabase(BaseApplication.book_db_Path, Context.MODE_PRIVATE,null);
		return db;
	}
	
	private SQLiteDatabase getdicDB()
	{
		SQLiteDatabase db = mContext.openOrCreateDatabase(BaseApplication.dic_db_Path, Context.MODE_PRIVATE, null);
		return db;
	}
	
	private SQLiteDatabase getdictionaryDB()
	{
		SQLiteDatabase db = mContext.openOrCreateDatabase(BaseApplication.dictionary_db_Path, Context.MODE_PRIVATE, null);
		return db;
	}
	
	/**
	 * ��ȡ�����ֵ����
	 * @return
	 */
	public List<BookCategory> getAllBookCats()
	{
		List<BookCategory> bookCategorys = new ArrayList<BookCategory>();
		String sql = "select CateID,CateName,CateOrder from tbCate order by CateOrder asc";
		SQLiteDatabase db = getbookDB();
		Cursor cur = db.rawQuery(sql, null);
		BookCategory category = null;
		while(cur.moveToNext())
		{
			category = new BookCategory();
			category.setCateID(cur.getInt(cur.getColumnIndex("CateID")));
			category.setCateName(cur.getString(cur.getColumnIndex("CateName")));
			category.setCateOrder(cur.getInt(cur.getColumnIndex("CateOrder")));
			bookCategorys.add(category);
		}
		db.close();
		return bookCategorys;
	}
	 
	/**
	 * ��ȡ�ֵ�����µĵ��ʱ�
	 * @param cateId
	 * @return
	 */
	public List<BookBook> getBooksByCateId(int cateId)
	{
		List<BookBook> books = new ArrayList<BookBook>();
		String sql = "select BookID,CateID,BookName,BookCount,BookOrder from tbBook where CateID=" + cateId + " order by BookOrder asc";
		SQLiteDatabase db = getbookDB();
		Cursor cursor = db.rawQuery(sql, null);
		BookBook book = null;
		while(cursor.moveToNext())
		{
			book = new BookBook();
			book.setBookId(cursor.getInt(cursor.getColumnIndex("BookID")));
			book.setCateId(cursor.getInt(cursor.getColumnIndex("CateID")));
			book.setBookName(cursor.getString(cursor.getColumnIndex("BookName")));
			book.setBookCount(cursor.getInt(cursor.getColumnIndex("BookCount")));
			book.setBookOrder(cursor.getInt(cursor.getColumnIndex("BookOrder")));
			books.add(book);
		}
		db.close();
		return books;
	}
	
	/**
	 * ���ݵ��ʱ���Ż�ȡ���ʱ���Ϣ
	 * @param bookId
	 * @return
	 */
	public BookBook getBookById(int bookId)
	{
		BookBook book = null;
		String sql = "select BookID,CateID,BookName,BookCount,BookOrder from tbBook where BookID=" + bookId;
		SQLiteDatabase db = getbookDB();
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			book = new BookBook();
			book.setBookId(cursor.getInt(cursor.getColumnIndex("BookID")));
			book.setCateId(cursor.getInt(cursor.getColumnIndex("CateID")));
			book.setBookName(cursor.getString(cursor.getColumnIndex("BookName")));
			book.setBookCount(cursor.getInt(cursor.getColumnIndex("BookCount")));
			book.setBookOrder(cursor.getInt(cursor.getColumnIndex("BookOrder")));
		}
		db.close();
		return book;
	}
}
