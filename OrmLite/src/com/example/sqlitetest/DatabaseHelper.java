package com.example.sqlitetest;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	    private final static String DATABASE_NAME="ormlite.db";
	    private final static int DATABASE_VERSION=1;
	    private final static String TABLE_NAME="sec_pwd";
	    public final static String FIELD_ID="_id"; 
	    public final static String FIELD_TITLE="sec_Title";
	    public Dao<SimpleData, Integer> simpleDao;
	    public String content ;
	    
	    public DatabaseHelper(Context context)
	    {
	        super(context, DATABASE_NAME,null, DATABASE_VERSION);
	    }

		@Override
		public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1)
		{
			try
			{
				TableUtils.createTable(arg1, SimpleData.class);
				
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}



		public Dao<SimpleData, Integer> getSimpleDataDao() throws SQLException
		{
			if(simpleDao == null)
			{
				simpleDao = getDao(SimpleData.class);
			}
			
			return simpleDao;
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1,
				int arg2, int arg3)
		{
			try {
				TableUtils.dropTable(connectionSource, SimpleData.class, true);
				// after we drop the old databases, we create the new ones
				onCreate(arg0, connectionSource);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}	   
}
